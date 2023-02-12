package com.piersoft.converse.vendor.service;

public interface IdentityVerificationService {

    String sendOTPTOVerifyVendorPhoneNumber(String phoneNumber);

    String verifyOTPSentToVendorPhoneNumber(String phoneNumber, String otp);
}
