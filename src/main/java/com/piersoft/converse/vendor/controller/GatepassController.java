package com.piersoft.converse.vendor.controller;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.piersoft.converse.vendor.config.S3Config;
import com.piersoft.converse.vendor.service.GatepassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.rekognition.model.*;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.sagemakerruntime.SageMakerRuntimeClient;
import software.amazon.awssdk.services.textract.TextractClient;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/converse/v1/gatepass")
public class GatepassController {

    @Autowired
    private GatepassService gatepassService;

    @GetMapping("/vehicleNo")
    public ResponseEntity<String> fetchVehicleNo(@RequestParam("file") MultipartFile multipart) throws FileNotFoundException {
        return ResponseEntity.ok().body(gatepassService.fetchVehicleNo(multipart));
    }
}
