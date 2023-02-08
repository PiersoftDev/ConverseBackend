package com.piersoft.converse.vendor.persistence.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.piersoft.converse.vendor.request.dto.VendorBankDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBDocument
public class VendorKYCDO {

    private String gstNumber;
    private boolean gstVerified;
    private String aadharNumber;
    private boolean aadharVerified;
    private VendorBankDetailsDO bankDetailsDO;
}
