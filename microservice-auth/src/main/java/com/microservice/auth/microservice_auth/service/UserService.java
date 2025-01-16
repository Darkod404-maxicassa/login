package com.microservice.auth.microservice_auth.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.microservice.auth.microservice_auth.entity.UserEntity;
import com.microservice.auth.microservice_auth.entity.models.UserRequest;

import lombok.NonNull;

public interface UserService {

    List<UserEntity> findAll();

    Page<UserEntity> findAll(Pageable pageable);

    Optional<UserEntity> findById(@NonNull Long id);

    UserEntity save(UserEntity user);

    Optional<UserEntity> update(UserRequest user, Long id);

    void deleteById(Long id);
}

