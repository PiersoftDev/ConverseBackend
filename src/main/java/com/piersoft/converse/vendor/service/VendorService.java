package com.piersoft.converse.vendor.service;

import com.piersoft.converse.vendor.request.dto.VendorOnboardRequestBody;

public interface VendorService {

    String onboardVendor(VendorOnboardRequestBody vendorOnboardRequestBody);

    void updateCompanyDetails(VendorOnboardRequestBody vendorOnboardRequestBody);

    void updateCompanyContactInfo(VendorOnboardRequestBody vendorOnboardRequestBody);

    void updateCompanyKYC(VendorOnboardRequestBody vendorOnboardRequestBody);

    void fetchVendorDetails(String vendorId);
}
