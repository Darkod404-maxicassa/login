package com.microservice.auth.microservice_auth.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class  AuditEntity {

    @Column(name = "usuario_creacion", nullable = false)
    private String createdBy;

    @Column(name = "usuario_actualizacion", nullable = false)
    private String updatedBy;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "fecha_actualizacion", nullable = false)
    private LocalDateTime updatedDate;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdDate = now;
        this.updatedDate = now;
        this.createdBy = "defaultUser"; 
        this.updatedBy = "defaultUser"; 
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedDate = LocalDateTime.now();
    }
}
