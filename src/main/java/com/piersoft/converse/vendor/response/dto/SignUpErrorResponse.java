package com.piersoft.converse.vendor.response.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignUpErrorResponse {

    private String errorCode;
    private String errorResponse;
}
