package com.piersoft.converse.vendor.controller;


import com.piersoft.converse.vendor.service.IdentityVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/converse/v1/verifications")
public class VendorVerificationController {

    @Autowired
    IdentityVerificationService identityVerificationService;

    @PostMapping("/send-otp/{phoneNumber}")
    public ResponseEntity<Object> sendOtpToPhoneNumber(@PathVariable String phoneNumber){
       String status = identityVerificationService.sendOTPTOVerifyVendorPhoneNumber(phoneNumber);
       return  ResponseEntity.ok().body(status);
    }

    @PostMapping("/verify-otp/{phoneNumber}/{otp}")
    public ResponseEntity<Object> verifyOtp(@PathVariable String phoneNumber, @PathVariable String otp){
        String status = identityVerificationService.verifyOTPSentToVendorPhoneNumber(phoneNumber,otp);
        return  ResponseEntity.ok().body(status);
    }
}
