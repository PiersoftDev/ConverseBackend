package com.piersoft.converse.vendor.service;

import com.piersoft.converse.vendor.persistence.model.ProjectDO;
import com.piersoft.converse.vendor.request.dto.ProjectRequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProjectService {

    ProjectDO onboardProject(ProjectRequestBody projectRequestBody);
    void updateProjectDetails(ProjectRequestBody projectRequestBody);
    List<ProjectDO> fetchProjectsInfo();
    ProjectDO uploadImages(String projectId, MultipartFile headerImage, List<MultipartFile> siteImages) throws Exception;
}
