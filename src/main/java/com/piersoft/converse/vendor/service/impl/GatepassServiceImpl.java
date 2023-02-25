package com.piersoft.converse.vendor.service.impl;

import com.piersoft.converse.vendor.persistence.model.GatepassDO;
import com.piersoft.converse.vendor.persistence.repository.GatepassRepository;
import com.piersoft.converse.vendor.service.GatepassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.cloudfront.CloudFrontUtilities;
import software.amazon.awssdk.services.cloudfront.model.CustomSignerRequest;
import software.amazon.awssdk.services.cloudfront.url.SignedUrl;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;
import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.rekognition.model.*;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.textract.TextractClient;
import software.amazon.awssdk.services.textract.model.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class GatepassServiceImpl implements GatepassService {

    @Autowired
    private RekognitionClient rekognitionClient;

    @Autowired
    private TextractClient textractClient;

    @Autowired
    private S3Client s3Client;

    @Autowired
    private GatepassRepository gatepassRepository;

    @Autowired
    private DynamoDbClient dynamoDbClient;

    private static final String regex
            = "^[A-Z]{2}[\\ -]{0,1}[0-9]{2}[\\ -]{0,1}[A-Z]{1,2}[\\ -]{0,1}[0-9]{4}$";


    @Override
    public GatepassDO createGatepass(String projectId, String driverName, String driverPhoneNumber, String material, MultipartFile gatepassVehicleImg, MultipartFile purchaseOrderImg) throws Exception {
        GatepassDO gatepass = GatepassDO.builder()
                .projectId(projectId)
                .driverName(driverName)
                .driverPhoneNumber(driverPhoneNumber)
                .material(material)
                .createdDateTime(ZonedDateTime.now(ZoneId.of( "Asia/Kolkata" )))
                .lastUpdatedTime(ZonedDateTime.now(ZoneId.of( "Asia/Kolkata" )))
                .status("Created")
                .build();

        String vehicleNo = fetchVehicleNo(gatepassVehicleImg);
        uploadImageToS3("gatepass-vehicle-no-images",gatepassVehicleImg.getOriginalFilename(), gatepassVehicleImg );;
        SignedUrl signedUrl = getCloudFrontSignedUrl("dfkxr9rbpkopu.cloudfront.net", gatepassVehicleImg.getOriginalFilename() );
        gatepass.setVehicleNo(vehicleNo);
        gatepass.setVehicleImgUrl(signedUrl.url());


        String poNumber = fetchPurchaseOrderNo(purchaseOrderImg);
        uploadImageToS3("gatepass-po-images",purchaseOrderImg.getOriginalFilename(), purchaseOrderImg);
        SignedUrl poNumberSignedUrl = getCloudFrontSignedUrl("d20xz40a665hj1.cloudfront.net", purchaseOrderImg.getOriginalFilename() );
        gatepass.setPoNumber(poNumber);
        gatepass.setPoNumberImgUrl(poNumberSignedUrl.url());
        return  gatepassRepository.save(gatepass);
    }

    @Override
    public List<GatepassDO> getAllGatepassForProject(String projectId) {
        ScanRequest scanRequest = ScanRequest.builder().tableName("gatepass").build();
        ScanResponse response = dynamoDbClient.scan(scanRequest);
        List<GatepassDO> gatepassDOS = new ArrayList<>();
        if(response != null && response.count() > 0){
            List<Map<String,AttributeValue>> items =  response.items();
            for(Map<String,AttributeValue> item : items){
                GatepassDO gatepassDO = GatepassDO.builder()
                        .projectId(item.get("projectId").s())
                        .status(item.get("status").s())
                        .material(item.get("material").s())
                        .driverPhoneNumber(item.get("driverPhoneNumber").s())
                        .driverName(item.get("driverName").s())
                        .id(item.get("id").s())
                        .lastUpdatedTime(ZonedDateTime.parse(item.get("lastUpdatedTime").s(), DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                        .createdDateTime(ZonedDateTime.parse(item.get("createdDateTime").s(), DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                        .poNumber(item.containsKey("poNumber")? item.get("poNumber").s():"")
                        .poNumberImgUrl(item.get("poNumberImgUrl").s())
                        .vehicleNo(item.containsKey("vehicleNo")? item.get("vehicleNo").s():"")
                        .vehicleImgUrl(item.get("vehicleImgUrl").s())
                        .build();
                gatepassDOS.add(gatepassDO);
            }
            return gatepassDOS;
        }
        return null;
    }

    public String fetchVehicleNo(MultipartFile multipart) {
        try {

            SdkBytes sourceBytes = SdkBytes.fromInputStream(multipart.getInputStream());
            Image souImage = Image.builder()
                    .bytes(sourceBytes)
                    .build();

            DetectTextRequest textRequest = DetectTextRequest.builder()
                    .image(souImage)
                    .build();

            DetectTextResponse textResponse = rekognitionClient.detectText(textRequest);
            List<TextDetection> textCollection = textResponse.textDetections();
            String combinedText = "";
            for (TextDetection text: textCollection) {
                if(text.confidence()>90.0)
                    combinedText += text.detectedText();
            }
            combinedText = combinedText.replace(".","");
            combinedText = combinedText.replace("-","");
            combinedText = combinedText.replace(" ","");

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

    public String fetchPurchaseOrderNo(MultipartFile multipart) {
        try {
            SdkBytes sourceBytes = SdkBytes.fromInputStream(multipart.getInputStream());

            // Get the input Document object as bytes
            Document myDoc = Document.builder()
                    .bytes(sourceBytes)
                    .build();

            DetectDocumentTextRequest detectDocumentTextRequest = DetectDocumentTextRequest.builder()
                    .document(myDoc)
                    .build();

            // Invoke the Detect operation
            DetectDocumentTextResponse textResponse = textractClient.detectDocumentText(detectDocumentTextRequest);
            List<Block> docInfo = textResponse.blocks();
            for (Block block : docInfo) {
                if(block.text() != null && block.text().contains("/PO")) {
                    return block.text();
                }
            }

            DocumentMetadata documentMetadata = textResponse.documentMetadata();
            System.out.println("The number of pages in the document is " +documentMetadata.pages());

        } catch (TextractException | FileNotFoundException e) {

            System.err.println(e.getMessage());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "";
    }

    private void uploadImageToS3(String bucketName, String originalFileName, MultipartFile file) throws IOException {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(originalFileName)
                .build();
        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
    }

    private SignedUrl getCloudFrontSignedUrl(String distributionId, String originalFileName) throws Exception {
        String protocol = "https";
        String resourcePath = "/"+ originalFileName;

        String cloudFrontUrl = new URL(protocol, distributionId, resourcePath).toString();
        // C:\piersoft\ConverseBackend\private_key.der
        ///home/ubuntu/private_key.der
        // C:\piersoft\ConverseBackend\private_key.der
        Path path = Paths.get("/home/ubuntu/private_key.der");
        //Path path = Paths.get("C:\\piersoft\\ConverseBackend\\private_key.der");

        CustomSignerRequest customSignerRequest = CustomSignerRequest.builder()
                .resourceUrl(cloudFrontUrl)
                .privateKey(path)
                .keyPairId("K3W5460YW1RJUS")
                .expirationDate(Instant.MAX)
                .build();

        CloudFrontUtilities cloudFrontUtilities = CloudFrontUtilities.create();
        return cloudFrontUtilities.getSignedUrlWithCustomPolicy(customSignerRequest);
    }


}
