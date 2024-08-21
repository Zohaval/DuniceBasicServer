package com.example.DavtyanBasicServer.dto.response.common_response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BaseSuccessResponse {

    private int statusCode = 1;

    private boolean success = true;
}
