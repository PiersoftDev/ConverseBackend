package com.piersoft.converse.vendor.persistence.repository;

import com.piersoft.converse.vendor.persistence.model.ProjectDO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends CrudRepository<ProjectDO, String> {
}
