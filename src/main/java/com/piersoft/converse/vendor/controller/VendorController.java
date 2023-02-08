package com.piersoft.converse.vendor.controller;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.*;
import com.piersoft.converse.vendor.persistence.model.Vendor;
import com.piersoft.converse.vendor.persistence.model.VendorCompanyDetailsDO;
import com.piersoft.converse.vendor.persistence.model.VendorContactInfoDO;
import com.piersoft.converse.vendor.persistence.repository.VendorRepository;
import com.piersoft.converse.vendor.request.dto.VendorContactInformation;
import com.piersoft.converse.vendor.request.dto.VendorOnboardRequestBody;
import com.piersoft.converse.vendor.service.VendorService;
import lombok.CustomLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping(value = "/converse/vendor")
@Slf4j
public class VendorController {

    @Autowired
    private VendorService vendorService;

    @PostMapping("/onboard-vendor")
    public ResponseEntity<Object> onboardVendorData(@RequestBody VendorOnboardRequestBody vendorOnboardRequestBody){
        String vendorDocId = vendorService.onboardVendor(vendorOnboardRequestBody);
        return ResponseEntity.ok().body(vendorDocId);
    }

    @PostMapping("/onboard-vendor/updateVendorCompanyDetails")
    public ResponseEntity<Object> updateVendorCompanyDetails(@RequestBody VendorOnboardRequestBody vendorOnboardRequestBody){
        vendorService.updateCompanyDetails(vendorOnboardRequestBody);
        return ResponseEntity.ok().body("Successfully updated the company details");
    }

    @PostMapping("/onboard-vendor/updateVendorContactInfo")
    public ResponseEntity<Object> updateVendorContactInfo(@RequestBody VendorOnboardRequestBody vendorOnboardRequestBody){
        vendorService.updateCompanyContactInfo(vendorOnboardRequestBody);
        return ResponseEntity.ok().body("Successfully updated the contact information");
    }

    @PostMapping("/onboard-vendor/updateVendorKYC")
    public ResponseEntity<Object> updateVendorKYC(@RequestBody VendorOnboardRequestBody vendorOnboardRequestBody){
        vendorService.updateCompanyKYC(vendorOnboardRequestBody);
        return ResponseEntity.ok().body("Successfully updated the vendor KYC");
    }

}
