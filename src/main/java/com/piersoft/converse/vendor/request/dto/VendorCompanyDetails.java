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

    private String companyName;
    private String companyType;
    private String companyProfile;
    private String service;
    private String companyWebsiteUrl;
}
