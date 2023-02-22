package com.piersoft.converse.vendor.controller;

import com.piersoft.converse.vendor.persistence.model.ProjectDO;
import com.piersoft.converse.vendor.request.dto.ProjectRequestBody;
import com.piersoft.converse.vendor.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/converse/v1/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping("/onboard")
    public ResponseEntity<ProjectDO> onboardProject(@RequestBody ProjectRequestBody projectRequestBody){
        ProjectDO projectDO = projectService.onboardProject(projectRequestBody);
        return ResponseEntity.ok().body(projectDO);
    }
}
