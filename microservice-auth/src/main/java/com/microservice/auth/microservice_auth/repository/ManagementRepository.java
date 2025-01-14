package com.microservice.auth.microservice_auth.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.microservice.auth.microservice_auth.entity.ManagementEntity;

@Repository
public interface ManagementRepository extends CrudRepository<ManagementEntity, Long>{

}
