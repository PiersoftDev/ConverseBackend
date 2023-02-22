package com.piersoft.converse.vendor.controller;


import com.piersoft.converse.vendor.service.IdentityVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/converse/v1/verifications")
@CrossOrigin
public class VendorVerificationController {

    @Autowired
    IdentityVerificationService identityVerificationService;

    @PostMapping("/{vendorId}/phoneNo/{phoneNumber}")
    public ResponseEntity<Object> sendOtpToPhoneNumber(@PathVariable String vendorId, @PathVariable String phoneNumber) {
        String status = identityVerificationService.sendOTPTOVerifyVendorPhoneNumber(vendorId, phoneNumber);
        return ResponseEntity.ok().body(status);
    }

    @PostMapping("/{vendorId}/verify-otp/{phoneNumber}/{otp}")
    public ResponseEntity<Object> verifyOtp(@PathVariable String vendorId, @PathVariable String phoneNumber, @PathVariable String otp) {
        String status = identityVerificationService.verifyOTPSentToVendorPhoneNumber(vendorId, phoneNumber, otp);
        return ResponseEntity.ok().body(status);
    }

    @PostMapping("/{vendorId}/verify-otp/{aadhaarNo}")
    public ResponseEntity<Object> sendAadhaarOtp(@PathVariable String vendorId, @PathVariable String aadhaarNo, @PathVariable String otp) {
        String status = identityVerificationService.verifyOTPSentToVendorPhoneNumber(vendorId, aadhaarNo, otp);
        return ResponseEntity.ok().body(status);
    }
}

//    @PostMapping("/{vendorId}/verify-otp/{phoneNumber}/{otp}")
//    public ResponseEntity<Object> verifyOtp(@PathVariable String vendorId,@PathVariable String phoneNumber, @PathVariable String otp){
//        String status = identityVerificationService.verifyOTPSentToVendorPhoneNumber(vendorId, phoneNumber,otp);
//        return  ResponseEntity.ok().body(status);
//    }
//}
