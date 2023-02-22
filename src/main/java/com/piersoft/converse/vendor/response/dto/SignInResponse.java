package com.piersoft.converse.vendor.response.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignInResponse {

    private String accessToken;
    private String tokenType;
    private String refreshToken;
    private String idToken;
    private Integer expiresIn;
}
