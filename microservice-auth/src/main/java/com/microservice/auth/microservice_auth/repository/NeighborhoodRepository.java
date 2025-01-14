package com.microservice.auth.microservice_auth.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.microservice.auth.microservice_auth.entity.NeighborhoodEntity;

@Repository
public interface NeighborhoodRepository extends CrudRepository<NeighborhoodEntity,Long>{

}
