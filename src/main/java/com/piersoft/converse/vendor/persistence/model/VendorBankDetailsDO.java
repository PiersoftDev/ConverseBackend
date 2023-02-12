package com.piersoft.converse.vendor.persistence.model;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBDocument
public class VendorBankDetailsDO {

    private String accountNumber;
    private String branch;
    private String ifscCode;
    private boolean isAccountVerified;
}
