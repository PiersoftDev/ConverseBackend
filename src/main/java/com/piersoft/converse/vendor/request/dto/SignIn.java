package com.piersoft.converse.vendor.request.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignIn {

    private String countryCode;
    private String phoneNumber;
    private String password;
}
