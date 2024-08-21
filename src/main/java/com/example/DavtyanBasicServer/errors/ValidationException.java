package com.example.DavtyanBasicServer.errors;

public class ValidationException extends RuntimeException {

    public ValidationException(ErrorCodes errorCode) {
        super(errorCode.getErrorCodeName());
    }
}
