package com.microservice.auth.microservice_auth.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.microservice.auth.microservice_auth.entity.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    // @Query("SELECT u FROM UserEntity u JOIN FETCH u.company WHERE u.username = :username")
    // Optional<UserEntity> findByUsername(@Param("username") String username);

    Page<UserEntity> findAll(Pageable pageable);

    Optional<UserEntity> findByUsername(String name);
}

