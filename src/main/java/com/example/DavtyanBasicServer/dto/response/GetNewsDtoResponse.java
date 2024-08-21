package com.example.DavtyanBasicServer.dto.response;

import java.util.List;

import com.example.DavtyanBasicServer.entity.TasksEntity;
import lombok.Data;

@Data
public class GetNewsDtoResponse<T> {

    private List<TasksEntity> content;

    private Long notReady;

    private Long numberOfElements;

    private Long ready;
}
