package com.example.DavtyanBasicServer.service.impl;

import java.util.List;
import java.util.Optional;

import com.example.DavtyanBasicServer.dto.request.ChangeStatusTodoDtoRequest;
import com.example.DavtyanBasicServer.dto.request.ChangeTextTodoDtoRequest;
import com.example.DavtyanBasicServer.dto.request.CreateTodoDtoRequest;
import com.example.DavtyanBasicServer.dto.response.GetNewsDtoResponse;
import com.example.DavtyanBasicServer.dto.response.common_response.BaseSuccessResponse;
import com.example.DavtyanBasicServer.dto.response.common_response.CustomSuccessResponse;
import com.example.DavtyanBasicServer.entity.TasksEntity;
import com.example.DavtyanBasicServer.errors.ErrorCodes;
import com.example.DavtyanBasicServer.errors.ValidationException;
import com.example.DavtyanBasicServer.repository.TaskRepository;
import com.example.DavtyanBasicServer.service.ToDoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ToDoServiceImpl implements ToDoService {

    @Autowired
    public TaskRepository taskRepository;

    @Override
    public CustomSuccessResponse<GetNewsDtoResponse<List<TasksEntity>>> getPaginated(int page, int perPage, Boolean status) {
        Pageable pageable = PageRequest.of(page - 1, perPage);
        List<TasksEntity> tasksEntityList = taskRepository.findAll(pageable).getContent();
        String taskStatus = String.valueOf(status);
        List<TasksEntity> tasksEntities =
                switch (taskStatus) {
                    case "true" -> tasksEntityList.stream().filter(e -> e.getStatus()).toList();
                    case "false" -> tasksEntityList.stream().filter(e -> !e.getStatus()).toList();
                    case "null" -> tasksEntityList;
                    default -> tasksEntityList;
                };
        GetNewsDtoResponse getNewsDtoResponse = new GetNewsDtoResponse();
        getNewsDtoResponse.setContent(tasksEntities);
        getNewsDtoResponse.setNumberOfElements(tasksEntities.stream().count());
        getNewsDtoResponse.setReady(tasksEntityList.stream().filter(TasksEntity::getStatus).count());
        getNewsDtoResponse.setNotReady(tasksEntityList.stream().filter(e -> !e.getStatus()).count());
        return new CustomSuccessResponse<>(getNewsDtoResponse);
    }

    @Override
    public CustomSuccessResponse<TasksEntity> createTask(CreateTodoDtoRequest createTodoDtoRequest) {
        TasksEntity tasksEntity = new TasksEntity();
        tasksEntity.setText(createTodoDtoRequest.getText());
        taskRepository.save(tasksEntity);
        return new CustomSuccessResponse<>(tasksEntity);
    }

    @Override
    public BaseSuccessResponse delete(Long id) {
        Optional<TasksEntity> tasksEntity = Optional.ofNullable(taskRepository.findById(id).orElseThrow(() ->
                new ValidationException(ErrorCodes.TASK_NOT_FOUND)));
        taskRepository.deleteById(id);
        return new BaseSuccessResponse();
    }

    @Override
    public BaseSuccessResponse deleteAllReady() {
        taskRepository.deleteAllByStatus(true);
        return new BaseSuccessResponse();
    }

    @Override
    public BaseSuccessResponse patchStatus(ChangeStatusTodoDtoRequest status, Long id) {
        TasksEntity tasksEntity = taskRepository.findById(id)
                .orElseThrow(() -> new ValidationException(ErrorCodes.TASK_NOT_FOUND));
        taskRepository.save(tasksEntity);
        return new BaseSuccessResponse();
    }

    @Override
    public BaseSuccessResponse patchAllStatuses(ChangeStatusTodoDtoRequest status) {
        taskRepository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new ValidationException(ErrorCodes.TASK_NOT_FOUND));
        taskRepository.patchAllStatus(status.getStatus());
        return new BaseSuccessResponse();
    }

    @Override
    public BaseSuccessResponse patchText(ChangeTextTodoDtoRequest text, Long id) {
        TasksEntity tasksEntity = taskRepository.findById(id)
                .orElseThrow(() -> new ValidationException(ErrorCodes.TASK_NOT_FOUND));
        tasksEntity.setText(text.getText());
        taskRepository.save(tasksEntity);
        return new BaseSuccessResponse();
    }
}
