package com.piersoft.converse.vendor.request.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VendorOnboardRequestBody {

    private String docId;
    private String vendorId;
    private VendorCompanyDetails companyDetails;
    private VendorContactInformation vendorContactInformation;
    private VendorKYC vendorKYC;


}
