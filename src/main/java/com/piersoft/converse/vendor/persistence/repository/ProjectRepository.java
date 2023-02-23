package com.piersoft.converse.vendor.persistence.repository;

import com.piersoft.converse.vendor.persistence.model.ProjectDO;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@EnableScan
public interface ProjectRepository extends CrudRepository<ProjectDO, String> {
}
