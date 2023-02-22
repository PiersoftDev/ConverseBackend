package com.piersoft.converse.vendor.request.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConfirmSignUp {

    private String confirmationCode;
    private String countryCode;
    private String phoneNumber;
}
