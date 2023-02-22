package com.piersoft.converse.vendor.response.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignUpSuccessResponse {

    private String userId;
    private boolean userConfirmed;
}
