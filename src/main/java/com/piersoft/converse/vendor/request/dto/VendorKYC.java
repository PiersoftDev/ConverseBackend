package com.piersoft.converse.vendor.request.dto;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTyped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VendorKYC {

    private String gstNumber;
    private boolean gstVerified;
    private String aadharNumber;
    private boolean aadharVerified;
    private VendorBankDetails vendorBankDetails;

}
