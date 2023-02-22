package com.piersoft.converse.vendor.service.impl;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.piersoft.converse.vendor.mapper.ProjectRequestDOMapper;
import com.piersoft.converse.vendor.persistence.model.ProjectDO;
import com.piersoft.converse.vendor.persistence.repository.ProjectRepository;
import com.piersoft.converse.vendor.request.dto.ProjectRequestBody;
import com.piersoft.converse.vendor.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;

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
//        PutObjectRequest putObjectRequest = new PutObjectRequest();
//        s3Client.putObject()
        ProjectDO projectDO = projectRequestDOMapper.requestBodyToDO(projectRequestBody);
        return projectRepository.save(projectDO);
    }

    @Override
    public void updateProjectDetails(ProjectRequestBody projectRequestBody) {

    }

    @Override
    public void fetchProjectsInfo() {

    }
}
