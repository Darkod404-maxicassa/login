package com.microservice.auth.microservice_auth.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.microservice.auth.microservice_auth.entity.ProfileEntity;

@Repository
public interface ProfileRepository extends CrudRepository<ProfileEntity,Long>{

    Optional<ProfileEntity> findByName(String name);
    
}
