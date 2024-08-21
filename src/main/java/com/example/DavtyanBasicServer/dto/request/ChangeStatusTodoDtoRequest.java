package com.example.DavtyanBasicServer.dto.request;

import com.example.DavtyanBasicServer.errors.ValidationConstants;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChangeStatusTodoDtoRequest {

    @NotNull(message = ValidationConstants.TODO_STATUS_NOT_NULL)
    private Boolean status;
}
