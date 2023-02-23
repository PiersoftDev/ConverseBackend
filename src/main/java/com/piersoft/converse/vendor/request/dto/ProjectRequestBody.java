package com.piersoft.converse.vendor.request.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectRequestBody {

    private String projectTitle;
    private String regionalOffice;
    private String addressLine1;
    private String addressLine2;
    private String state;
    private String contactNumber;
}
