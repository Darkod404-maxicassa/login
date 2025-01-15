package com.microservice.auth.microservice_auth.entity;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "usuarios")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario")
    private String username;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tipo_identificacion_id")
    private IdentificationTypeEntity identificationType;
 
    @Column(name = "primer_nombre", length = 500)
    private String firstName;

    @Column(name = "segundo_nombre", length = 500)
    private String secondName;

    @Column(name = "primer_apellido", length = 500)
    private String firstLastName;

    @Column(name = "segundo_apellido", length = 500)
    private String secondLastName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sexo_id")
    private GenderEntity gender;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "estado_civil_id")
    private CivilStatusEntity civilStatusEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tipo_sangre_id")
    private BloodTypeEntity bloodType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "direccion_id")
    private AddressEntity address;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dato_laboral_id")
    private EmploymentDataEntity employmentData;

    @Column(name = "tiene_libreta_militar")
    private Boolean hasMilitaryCard;

    @Column(name = "clase_libreta_militar")
    private Integer militaryCardClass;

    @Column(name = "fecha_nacimiento")
    private Date dateOfBirth;

    @Column(name = "correo", length = 500)
    private String email;

    @Column(name = "celular", length = 500)
    private String mobile;

    @NotBlank
    @Size(max = 80)
    @Column(name = "documento",  unique = true)
    private String document;

    @NotBlank
    @Column(name = "contrasenia")
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private CompanyEntity company;

    @ManyToOne(fetch = FetchType.EAGER)
    //@JoinTable(name = "usuarios_perfiles", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "perfil_id"))
    @JoinColumn(name = "perfil_id")
    private ProfileEntity profile; 


}