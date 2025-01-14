package com.microservice.auth.microservice_auth.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.microservice.auth.microservice_auth.entity.CostCenterEntity;

@Repository
public interface CostCenterRepository extends CrudRepository<CostCenterEntity,Long>{

}
