package com.piersoft.converse.vendor.request.dto;


import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class ProjectRequestBody {

    private String projectTitle;
    private String regionalOffice;
    private String addressLine1;
    private String addressLine2;
    private String state;
    private String contactNumber;
    private MultipartFile multipartFile;
}
