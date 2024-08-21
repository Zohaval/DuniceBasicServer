package com.example.DavtyanBasicServer.dto.response.common_response;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CustomSuccessResponse<T> {

    private final T data;

    private int[] statusCode;

    private Boolean success = true;
}
