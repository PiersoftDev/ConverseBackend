package com.piersoft.converse.vendor.controller;

import com.piersoft.converse.vendor.persistence.model.ProjectDO;
import com.piersoft.converse.vendor.request.dto.ProjectRequestBody;
import com.piersoft.converse.vendor.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/converse/v1/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping("/onboard")
    public ResponseEntity<ProjectDO> onboardProject( @RequestBody ProjectRequestBody projectRequestBody ){
        ProjectDO projectDO = projectService.onboardProject(projectRequestBody);
        return ResponseEntity.ok().body(projectDO);
    }

    @PostMapping("/images")
    public ResponseEntity<ProjectDO> uploadImages(@RequestParam("projectId") String projectId ,
                                                  @RequestParam("headerImage")MultipartFile headerImage,
                                                  @RequestParam("siteImages")List<MultipartFile> siteImages) throws Exception {
       ProjectDO projectDO = projectService.uploadImages(projectId, headerImage, siteImages);
       return ResponseEntity.ok().body(projectDO);
    }


    @GetMapping("/")
    public ResponseEntity<List<ProjectDO>> listProjects(){
        List<ProjectDO> projectDOList = projectService.fetchProjectsInfo();
        return ResponseEntity.ok().body(projectDOList);
    }
}
