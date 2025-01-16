package com.microservice.auth.microservice_auth.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.microservice.auth.microservice_auth.entity.CountryEntity;

@Repository
public interface CountryRepository extends CrudRepository<CountryEntity, Long>{

}
