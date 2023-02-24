package com.piersoft.converse.vendor.persistence.repository;

import com.piersoft.converse.vendor.persistence.model.GatepassDO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GatepassRepository extends CrudRepository<GatepassDO, String> {
}
