package com.piersoft.converse.vendor.service;

public interface IdentityVerificationService {

    String sendOTPTOVerifyVendorPhoneNumber(String vendorId, String phoneNumber);

    String verifyOTPSentToVendorPhoneNumber(String vendorId, String phoneNumber, String otp);
}
