package com.piersoft.converse.vendor.service.impl;

import com.piersoft.converse.vendor.service.GatepassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.rekognition.model.*;
import software.amazon.awssdk.services.s3.S3Client;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class GatepassServiceImpl implements GatepassService {

    @Autowired
    private RekognitionClient rekognitionClient;

    @Autowired
    private S3Client s3Client;

    private static final String regex
            = "^[A-Z]{2}[\\ -]{0,1}[0-9]{2}[\\ -]{0,1}[A-Z]{1,2}[\\ -]{0,1}[0-9]{4}$";

    @Override
    public String fetchVehicleNo(MultipartFile multipart) {
        try {
//            String bucketName, String key, InputStream input,
//                    ObjectMetadata metadata
//            PutObjectRequest putObjectRequest = new PutObjectRequest()
//            s3Client.putObject(putObjectRequest);
            SdkBytes sourceBytes = SdkBytes.fromInputStream(multipart.getInputStream());
            Image souImage = Image.builder()
                    .bytes(sourceBytes)
                    .build();

            DetectTextRequest textRequest = DetectTextRequest.builder()
                    .image(souImage)
                    .build();

            DetectTextResponse textResponse = rekognitionClient.detectText(textRequest);
            List<TextDetection> textCollection = textResponse.textDetections();
            System.out.println("Detected lines and words");
            String combinedText = "";
            for (TextDetection text: textCollection) {
                combinedText += text.detectedText();
            }
            combinedText = combinedText.replace(".","");
            combinedText = combinedText.replace("-","");

            // Compile the ReGex
            Pattern p = Pattern.compile(regex);
            for(int i = 0 ; i < combinedText.length()-10; i++){
                String temp = combinedText.substring(i, i+10);
                Matcher m = p.matcher(temp);
                if(m.matches()){
                    return m.group();
                }
            }
        } catch (RekognitionException | IOException e) {
            return e.getMessage();
        }
        return "";
    }
}
