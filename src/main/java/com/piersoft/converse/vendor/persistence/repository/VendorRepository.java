package com.piersoft.converse.vendor.persistence.repository;

import com.piersoft.converse.vendor.persistence.model.Vendor;
import org.socialsignin.spring.data.dynamodb.repository.DynamoDBCrudRepository;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface VendorRepository extends DynamoDBCrudRepository<Vendor, String> {

}
