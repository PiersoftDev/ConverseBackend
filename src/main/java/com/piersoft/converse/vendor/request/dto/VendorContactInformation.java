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

    private String companyAddressLane1;
    private String companyAddressLane2;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String companyEmail;
    private boolean companyEmailVerified;
    private String companyPhoneNo;
    private boolean companyPhoneNoVerified;

}
