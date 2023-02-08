package com.piersoft.converse.vendor.request.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VendorBankDetails {

    private String bankAccountNumber;
    private String branch;
    private String ifscCode;
    private boolean isAccountVerified;
}
