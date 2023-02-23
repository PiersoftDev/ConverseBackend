package com.piersoft.converse.vendor.service.impl;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.piersoft.converse.vendor.mapper.ProjectRequestDOMapper;
import com.piersoft.converse.vendor.persistence.model.ProjectDO;
import com.piersoft.converse.vendor.persistence.repository.ProjectRepository;
import com.piersoft.converse.vendor.request.dto.ProjectRequestBody;
import com.piersoft.converse.vendor.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.cloudfront.CloudFrontUtilities;
import software.amazon.awssdk.services.cloudfront.model.CustomSignerRequest;
import software.amazon.awssdk.services.cloudfront.url.SignedUrl;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private S3Client s3Client;

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectRequestDOMapper projectRequestDOMapper;

    @Override
    public ProjectDO onboardProject(ProjectRequestBody projectRequestBody) {
        ProjectDO projectDO = projectRequestDOMapper.requestBodyToDO(projectRequestBody);
        projectDO.setSiteImageUrls(new ArrayList<>());
        return projectRepository.save(projectDO);
    }

    @Override
    public ProjectDO uploadImages(String projectId, MultipartFile headerImage, List<MultipartFile> siteImages) throws Exception {
        SignedUrl headerImgSignedUrl = uploadProjectHeaderImage(projectId, headerImage);
        List<String> siteImgSignedUrls = uploadProjectImage(projectId, siteImages);
        Optional<ProjectDO> projectDOOptional = projectRepository.findById(projectId);
        if(projectDOOptional.isPresent()){
            ProjectDO projectDO = projectDOOptional.get();
            projectDO.setHeaderImgUrl(headerImgSignedUrl.url());
            projectDO.setSiteImageUrls(siteImgSignedUrls);
            projectRepository.save(projectDO);
            return projectDO;
        }
        return null;
    }


    private SignedUrl uploadProjectHeaderImage(String id, MultipartFile file) throws Exception {
        String originalFileName = file.getOriginalFilename();
        uploadImageToS3("converse-project-images", id, originalFileName,file);
        return  getCloudFrontSignedUrl(id, originalFileName);
    }


    private List<String> uploadProjectImage(String id, List<MultipartFile> files) throws Exception {
        List<String> signedUrls = new ArrayList<>();
        for(MultipartFile file : files) {
            String originalFileName = file.getOriginalFilename();
            uploadImageToS3("converse-project-images", id, originalFileName, file);
            SignedUrl signedUrl = getCloudFrontSignedUrl(id, originalFileName);
            signedUrls.add(signedUrl.url());
        }
        return signedUrls;
    }

    private SignedUrl getCloudFrontSignedUrl(String id, String originalFileName) throws Exception {
        String protocol = "https";
        String resourcePath = "/" +id+"/"+ originalFileName;

        String cloudFrontUrl = new URL(protocol, "d3cdac72dpvma6.cloudfront.net", resourcePath).toString();
        Path path = Paths.get("C:\\piersoft\\ConverseBackend\\private_key.der");

        CustomSignerRequest customSignerRequest = CustomSignerRequest.builder()
                .resourceUrl(cloudFrontUrl)
                .privateKey(path)
                .keyPairId("K3W5460YW1RJUS")
                .expirationDate(Instant.MAX)
                .build();

        CloudFrontUtilities cloudFrontUtilities = CloudFrontUtilities.create();
        return cloudFrontUtilities.getSignedUrlWithCustomPolicy(customSignerRequest);
    }

    private void uploadImageToS3(String bucketName, String projectId, String originalFileName, MultipartFile file) throws IOException {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(projectId+"/"+originalFileName)
                .build();
        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
    }

    @Override
    public void updateProjectDetails(ProjectRequestBody projectRequestBody) {

    }

    @Override
    public List<ProjectDO> fetchProjectsInfo() {
        return (List<ProjectDO>)projectRepository.findAll();
    }


}
