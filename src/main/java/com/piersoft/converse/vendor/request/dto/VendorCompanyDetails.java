package com.piersoft.converse.vendor.request.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VendorCompanyDetails {

    private String name;
    private String type;
    private String profile;
    private String service;
    private String websiteURL;
}
