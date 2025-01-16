package com.microservice.auth.microservice_auth.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.microservice.auth.microservice_auth.entity.UserStatusEntity;

@Repository
public interface UserStatusRepository extends CrudRepository<UserStatusEntity,Long>{

}
