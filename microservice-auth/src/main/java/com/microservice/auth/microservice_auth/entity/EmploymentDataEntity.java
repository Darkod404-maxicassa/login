package com.microservice.auth.microservice_auth.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "datos_laborales")
public class EmploymentDataEntity extends AuditEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "empresa_id")
    private CompanyEntity companyId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "centro_costo_id")
    private CostCenterEntity costCenterId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cargo_id")
    private PositionEntity positionId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gerencia_id")
    private ManagementEntity managementId;

    @Column(name = "fecha_ingreso")
    private LocalDate dateOfEntry;

    @Column(name = "fecha_inicio_contrato")
    private LocalDate contractStartDate;
 
    @Column(name = "fecha_fin_contrato")
    private LocalDate contractEndDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "estado_id")
    private UserStatusEntity userStateId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "causa_retiro_id")
    private RetirementCauseEntity retirementCauseEntityId;
}