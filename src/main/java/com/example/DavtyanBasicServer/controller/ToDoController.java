package com.example.DavtyanBasicServer.controller;

import java.util.List;

import com.example.DavtyanBasicServer.dto.request.ChangeStatusTodoDtoRequest;
import com.example.DavtyanBasicServer.dto.request.ChangeTextTodoDtoRequest;
import com.example.DavtyanBasicServer.dto.request.CreateTodoDtoRequest;
import com.example.DavtyanBasicServer.dto.response.GetNewsDtoResponse;
import com.example.DavtyanBasicServer.dto.response.common_response.BaseSuccessResponse;
import com.example.DavtyanBasicServer.dto.response.common_response.CustomSuccessResponse;
import com.example.DavtyanBasicServer.entity.TasksEntity;
import com.example.DavtyanBasicServer.errors.ValidationConstants;
import com.example.DavtyanBasicServer.service.ToDoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/v1/todo")
public class ToDoController {

    @Autowired
    private ToDoService toDoService;

    @GetMapping
    public ResponseEntity<CustomSuccessResponse<GetNewsDtoResponse<List<TasksEntity>>>> getPaginated(
            @RequestParam @NotNull(message = ValidationConstants.TASKS_PAGE_GREATER_OR_EQUAL_1) @Length(min = 1) int page,
            @RequestParam @NotNull(message = ValidationConstants.TASKS_PER_PAGE_GREATER_OR_EQUAL_1) @Length(min = 1) int perPage,
            @RequestParam(required = false) Boolean status) {
        return ResponseEntity.ok(toDoService.getPaginated(page, perPage, status));
    }

    @PostMapping
    public ResponseEntity<CustomSuccessResponse<TasksEntity>> createTask(@Valid @RequestBody CreateTodoDtoRequest createTodoDtoRequest) {
        return ResponseEntity.ok(toDoService.createTask(createTodoDtoRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseSuccessResponse> delete(@PathVariable @NotNull @Positive(message = ValidationConstants.ID_MUST_BE_POSITIVE) Long id) {
        return ResponseEntity.ok(toDoService.delete(id));
    }

    @DeleteMapping
    public ResponseEntity<BaseSuccessResponse> deleteAllReady() {
        return ResponseEntity.ok(toDoService.deleteAllReady());
    }

    @PatchMapping("/status/{id}")
    public ResponseEntity<BaseSuccessResponse> patchStatus(@Valid @RequestBody ChangeStatusTodoDtoRequest status,
                                                           @PathVariable @NotNull(message = ValidationConstants.TASK_NOT_FOUND) Long id) {
        return ResponseEntity.ok(toDoService.patchStatus(status, id));
    }

    @PatchMapping
    public ResponseEntity<BaseSuccessResponse> patchAllStatuses(@Valid @RequestBody ChangeStatusTodoDtoRequest status) {
        return ResponseEntity.ok(toDoService.patchAllStatuses(status));
    }

    @PatchMapping("/text/{id}")
    public ResponseEntity<BaseSuccessResponse> patchText(@Valid @RequestBody ChangeTextTodoDtoRequest text, @PathVariable Long id) {
        return ResponseEntity.ok(toDoService.patchText(text, id));
    }
}
