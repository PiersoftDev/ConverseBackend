package com.piersoft.converse.vendor.controller;

import com.piersoft.converse.vendor.persistence.model.GatepassDO;
import com.piersoft.converse.vendor.service.GatepassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/converse/v1/gatepass")
public class GatepassController {

    @Autowired
    private GatepassService gatepassService;

    @RequestMapping(value = "/", headers = "Content-Type= multipart/form-data", method = RequestMethod.POST)
    public ResponseEntity<GatepassDO> createGatepass(@RequestParam("driverName") String driverName,
                                                     @RequestParam("driverPhoneNumber") String driverPhoneNumber,
                                                     @RequestParam("material") String material,
                                                     @RequestParam("gatepassVehicleImg") MultipartFile gatepassVehicleImg,
                                                     @RequestParam("purchaseOrderImg") MultipartFile purchaseOrderImg) throws Exception {
        return ResponseEntity.ok().body(gatepassService.createGatepass(driverName, driverPhoneNumber, material, gatepassVehicleImg, purchaseOrderImg));
    }
}
