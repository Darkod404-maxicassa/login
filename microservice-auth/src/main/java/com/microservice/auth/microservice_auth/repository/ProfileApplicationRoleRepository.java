package com.microservice.auth.microservice_auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.microservice.auth.microservice_auth.entity.ProfileApplicationRoleEntity;

@Repository
public interface ProfileApplicationRoleRepository extends CrudRepository<ProfileApplicationRoleEntity, Long> {

    // @Query("SELECT pa.id, pa.application.id , pa.application.name , pa.role.id AS role_id, pa.role.name AS role_name " +
    //    "FROM ProfileApplicationRoleEntity pa " +
    //    "WHERE pa.pprofile.id = :perfilId")
    // List<Object[]> findApplicationsAndRolesByPerfilId(Long perfilId);

}
