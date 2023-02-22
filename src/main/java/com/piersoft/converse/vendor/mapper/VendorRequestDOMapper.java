package com.piersoft.converse.vendor.mapper;

import com.piersoft.converse.vendor.persistence.model.Vendor;
import com.piersoft.converse.vendor.request.dto.VendorOnboardRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VendorRequestDOMapper {
    @Mapping(target="companyDetailsDO", source="requestBody.companyDetails")
    @Mapping(target="contactInfoDO", source="requestBody.contactInformation")
    @Mapping(target="kycDO", source="requestBody.kyc")
    Vendor requestBodyToDO(VendorOnboardRequestBody requestBody);
}
