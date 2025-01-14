package com.microservice.auth.microservice_auth.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.microservice.auth.microservice_auth.entity.ApplicationEntity;

@Repository
public interface ApplicationRepository extends CrudRepository<ApplicationEntity, Long>{

}
