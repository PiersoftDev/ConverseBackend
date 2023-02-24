package com.piersoft.converse.vendor.service;

import com.piersoft.converse.vendor.persistence.model.GatepassDO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface GatepassService {

    GatepassDO createGatepass(String driverName, String driverPhoneNumber, String material, MultipartFile gatepassVehicleImg, MultipartFile purchaseOrderImg) throws Exception;
}
