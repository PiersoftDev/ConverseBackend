package com.piersoft.converse.vendor.service;

import com.piersoft.converse.vendor.persistence.model.ProjectDO;
import com.piersoft.converse.vendor.request.dto.ProjectRequestBody;

public interface ProjectService {

    ProjectDO onboardProject(ProjectRequestBody projectRequestBody);

    void updateProjectDetails(ProjectRequestBody projectRequestBody);

    void fetchProjectsInfo();


}
