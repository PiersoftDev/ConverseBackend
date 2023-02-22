package com.piersoft.converse.vendor.request.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignUp {

    private String persona;
    private String countryCode;
    private String whatsappNumber;
    private String password;

}
