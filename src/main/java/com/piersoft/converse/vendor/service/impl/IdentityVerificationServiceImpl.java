package com.piersoft.converse.vendor.service.impl;

import com.piersoft.converse.vendor.service.IdentityVerificationService;
import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import org.springframework.stereotype.Service;

@Service
public class IdentityVerificationServiceImpl implements IdentityVerificationService {

    private static String ACCOUNT_SID = "AC422a7fed74f3d18b56b02eba1ec7e09b";
    private static String AUTH_TOKEN= "18b5a2880420d37d402adcd38afd1b2c";
    private static String pathServiceId = "VA5a66ca90e4c589c983d5cfc0441ff082";
    private static String channel = "sms";

    @Override
    public String sendOTPTOVerifyVendorPhoneNumber(String phoneNumber) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Verification verification = Verification.creator(
                        pathServiceId,
                        phoneNumber,
                        channel)
                .create();
        return verification.getStatus();
    }

    @Override
    public String verifyOTPSentToVendorPhoneNumber(String phoneNumber, String otp) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        VerificationCheck verificationCheck = VerificationCheck.creator(
                        pathServiceId)
                .setTo(phoneNumber)
                .setCode(otp)
                .create();
        return  verificationCheck.getStatus();
    }
}
