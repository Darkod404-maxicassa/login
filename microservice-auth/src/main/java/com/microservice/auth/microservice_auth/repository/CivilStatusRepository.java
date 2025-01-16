package com.microservice.auth.microservice_auth.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.microservice.auth.microservice_auth.entity.CivilStatusEntity;

@Repository
public interface CivilStatusRepository extends CrudRepository<CivilStatusEntity,Long>{

}
