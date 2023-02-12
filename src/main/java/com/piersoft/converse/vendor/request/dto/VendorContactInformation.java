package com.piersoft.converse.vendor.request.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VendorContactInformation {

    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String email;
    private boolean emailVerified;
    private String phoneNo;
    private boolean phoneNoVerified;

}
