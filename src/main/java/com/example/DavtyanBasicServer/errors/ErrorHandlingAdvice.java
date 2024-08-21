package com.example.DavtyanBasicServer.errors;

import java.util.List;

import com.example.DavtyanBasicServer.dto.response.common_response.BaseSuccessResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandlingAdvice {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<BaseSuccessResponse> handleException(ValidationException e) {
        var error = ErrorCodes.codes.get(e.getMessage());
        BaseSuccessResponse baseSuccessResponse = new BaseSuccessResponse(error, true);
        return ResponseEntity.badRequest().body(baseSuccessResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseSuccessResponse> handlerExceptionMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        var messageSet = e.getBindingResult().getAllErrors();
        List<Integer> errors = messageSet
                .stream()
                .map(err -> err.getDefaultMessage())
                .map(err -> ErrorCodes.codes.get(err))
                .distinct()
                .toList();
        BaseSuccessResponse baseSuccessResponse = new BaseSuccessResponse(errors.get(0), true);
        return new ResponseEntity<>(baseSuccessResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<BaseSuccessResponse> handleExceptionConstraintViolationException(ConstraintViolationException e) {
        var messageSet = e.getConstraintViolations();
        List<Integer> errors = messageSet
                .stream()
                .map(ConstraintViolation::getMessage)
                .map(err -> ErrorCodes.codes.get(err))
                .distinct()
                .toList();
        BaseSuccessResponse baseSuccessResponse = new BaseSuccessResponse(errors.get(0), true);
        return new ResponseEntity<>(baseSuccessResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new BaseSuccessResponse(ErrorCodes.codes.get(ValidationConstants.HTTP_MESSAGE_NOT_READABLE_EXCEPTION), true)
        );
    }
}
