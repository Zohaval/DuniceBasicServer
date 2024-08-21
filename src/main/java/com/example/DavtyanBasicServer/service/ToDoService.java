package com.example.DavtyanBasicServer.service;

import java.util.List;

import com.example.DavtyanBasicServer.dto.request.ChangeStatusTodoDtoRequest;
import com.example.DavtyanBasicServer.dto.request.ChangeTextTodoDtoRequest;
import com.example.DavtyanBasicServer.dto.request.CreateTodoDtoRequest;
import com.example.DavtyanBasicServer.dto.response.GetNewsDtoResponse;
import com.example.DavtyanBasicServer.dto.response.common_response.BaseSuccessResponse;
import com.example.DavtyanBasicServer.dto.response.common_response.CustomSuccessResponse;
import com.example.DavtyanBasicServer.entity.TasksEntity;

public interface ToDoService {

    CustomSuccessResponse<GetNewsDtoResponse<List<TasksEntity>>> getPaginated(int page, int perPage, Boolean status);

    CustomSuccessResponse<TasksEntity> createTask(CreateTodoDtoRequest createTodoDtoRequest);

    BaseSuccessResponse delete(Long id);

    BaseSuccessResponse deleteAllReady();

    BaseSuccessResponse patchStatus(ChangeStatusTodoDtoRequest status, Long id);

    BaseSuccessResponse patchAllStatuses(ChangeStatusTodoDtoRequest status);

    BaseSuccessResponse patchText(ChangeTextTodoDtoRequest text, Long id);
}
