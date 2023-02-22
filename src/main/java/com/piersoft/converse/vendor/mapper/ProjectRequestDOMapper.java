package com.piersoft.converse.vendor.mapper;

import com.piersoft.converse.vendor.persistence.model.ProjectDO;
import com.piersoft.converse.vendor.request.dto.ProjectRequestBody;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectRequestDOMapper {
    ProjectDO requestBodyToDO(ProjectRequestBody requestBody);
}
