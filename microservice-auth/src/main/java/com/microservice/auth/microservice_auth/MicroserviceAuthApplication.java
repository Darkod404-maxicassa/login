package com.microservice.auth.microservice_auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.microservice.auth.microservice_auth.entity.ApplicationEntity;
import com.microservice.auth.microservice_auth.entity.ApplicationGroupEntity;
import com.microservice.auth.microservice_auth.entity.CompanyEntity;
import com.microservice.auth.microservice_auth.entity.ProfileApplicationRoleEntity;
import com.microservice.auth.microservice_auth.entity.ProfileEntity;
import com.microservice.auth.microservice_auth.entity.RoleEntity;
import com.microservice.auth.microservice_auth.entity.StateEntity;
import com.microservice.auth.microservice_auth.entity.UserEntity;
import com.microservice.auth.microservice_auth.entity.UserProfileEntity;
import com.microservice.auth.microservice_auth.repository.ApplicationGroupRepository;
import com.microservice.auth.microservice_auth.repository.ApplicationRepository;
import com.microservice.auth.microservice_auth.repository.CompanyRepository;
import com.microservice.auth.microservice_auth.repository.ProfileApplicationRoleRepository;
import com.microservice.auth.microservice_auth.repository.ProfileRepository;
import com.microservice.auth.microservice_auth.repository.RoleRepository;
import com.microservice.auth.microservice_auth.repository.StateRepository;
import com.microservice.auth.microservice_auth.repository.UserProfileRepository;
import com.microservice.auth.microservice_auth.repository.UserRepository;

@SpringBootApplication
public class MicroserviceAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceAuthApplication.class, args);
	}

	@Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*") //URLS
                        .allowedMethods("*")
                        .allowedHeaders("*");
            }
        };
    }

	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
    private CompanyRepository companyRepository;

	@Autowired
    private ApplicationGroupRepository applicationGroupRepository;

	@Autowired
    private ApplicationRepository applicationRepository;

	@Autowired
    private RoleRepository  roleRepository;

	@Autowired
    private ProfileApplicationRoleRepository profileApplicationRoleRepository;

	@Autowired
    private UserRepository userRepository;

	@Autowired
    private UserProfileRepository userProfileRepository;

	@Autowired
    private StateRepository stateRepository;


	@Bean
	CommandLineRunner init(){

		return args -> {

			//! ESTADOS

			StateEntity statusEntity1 = StateEntity.builder()
				.name("ACTIVO")
				.build();

			stateRepository.save(statusEntity1);

			//! PERFILES

			ProfileEntity profileEntity1 = ProfileEntity.builder()
				.name("PERFIL_ADMIN")
				.state(statusEntity1)
				.build();

			profileRepository.save(profileEntity1);

			ProfileEntity profileEntity2 = ProfileEntity.builder()
				.name("PERFIL_USER")
				.state(statusEntity1)
				.build();

			profileRepository.save(profileEntity2);



			//! EMPRESAS

			CompanyEntity companyEntity = CompanyEntity.builder()
			.name("PEGOMAX")

				.build();

			companyRepository.save(companyEntity);

			companyEntity = CompanyEntity.builder()
				.name("TUCASSA")
				.build();

			companyRepository.save(companyEntity);

			companyEntity = CompanyEntity.builder()
				.name("MAXICASSA")
				.build();

			companyRepository.save(companyEntity);


			//!  GRUPO DE APLICACIONES

			ApplicationGroupEntity applicationGroupEntity = ApplicationGroupEntity.builder()
				.name("HRM")
				.build();

			applicationGroupRepository.save(applicationGroupEntity);

			ApplicationGroupEntity applicationGroupEntity2 = ApplicationGroupEntity.builder()
				.name("CRM")
				.build();

			applicationGroupRepository.save(applicationGroupEntity2);


			//! APLICACIONES

			ApplicationEntity applicationEntity1 = ApplicationEntity.builder()
				.name("DELTA")
				.applicationGroup(applicationGroupEntity)
				.build();

			applicationRepository.save(applicationEntity1);

			ApplicationEntity applicationEntity11 = ApplicationEntity.builder()
				.name("HEFESTO")
				.applicationGroup(applicationGroupEntity)
				.build();

			applicationRepository.save(applicationEntity11);

			ApplicationEntity applicationEntity111 = ApplicationEntity.builder()
				.name("ZEUS")
				.applicationGroup(applicationGroupEntity)
				.build();

			applicationRepository.save(applicationEntity111);

			ApplicationEntity applicationEntity2 = ApplicationEntity.builder()
				.name("HERMES")
				.applicationGroup(applicationGroupEntity2)
				.build();

			applicationRepository.save(applicationEntity2);

			ApplicationEntity applicationEntity22 = ApplicationEntity.builder()
				.name("POPEYE")
				.applicationGroup(applicationGroupEntity2)
				.build();

			applicationRepository.save(applicationEntity22);

			ApplicationEntity applicationEntity222 = ApplicationEntity.builder()
				.name("EL MARINO")
				.applicationGroup(applicationGroupEntity2)
				.build();

			applicationRepository.save(applicationEntity222);


			//! ROLES

			RoleEntity roleEntity = RoleEntity.builder()
				.name("USER")
				.build();

			roleRepository.save(roleEntity);

			RoleEntity roleEntity2 = RoleEntity.builder()
				.name("ADMIN")
				.build();

			roleRepository.save(roleEntity2);

			RoleEntity roleEntity3 = RoleEntity.builder()
				.name("SUPERADMIN")
				.build();

			roleRepository.save(roleEntity3);


			//!PERFILES_APLICACIONES_ROLES

			ProfileApplicationRoleEntity applicationRoleEntity = ProfileApplicationRoleEntity.builder()
				.profile(profileEntity1)
				.application(applicationEntity1)
				.role(roleEntity)
				.build();

				profileApplicationRoleRepository.save(applicationRoleEntity);

				applicationRoleEntity = ProfileApplicationRoleEntity.builder()
				.profile(profileEntity2)
				.application(applicationEntity11)
				.role(roleEntity2)
				.build();

				profileApplicationRoleRepository.save(applicationRoleEntity);

				applicationRoleEntity = ProfileApplicationRoleEntity.builder()
				.profile(profileEntity2)
				.application(applicationEntity2)
				.role(roleEntity3)
				.build();

				profileApplicationRoleRepository.save(applicationRoleEntity);


			//!USUARIOS

			UserEntity userEntity1 = UserEntity.builder()
				.username("1")
				.document("1")
				.password("$2a$12$VfKEd56705EZC31pNWXaFeO8Y.c1PUj8Klp.sAQC4k40CIkmMcSkS")
				.build();

			userRepository.save(userEntity1);

			UserEntity userEntity2 = UserEntity.builder()
				.username("2")
				.document("2")
				.password("$2a$12$xOY9NZZUZHxY7TE/06WKAOAw9UqxkqTJFj0wifFztfGRfVhRiNtiK")
				.build();

			userRepository.save(userEntity2);


            //!USUARIOS_PERFILES

			UserProfileEntity userProfileEntity1 = UserProfileEntity.builder()
				.user(userEntity1)
				.profile(profileEntity1)
				.build();

			userProfileRepository.save(userProfileEntity1);

			UserProfileEntity userProfileEntity2 = UserProfileEntity.builder()
				.user(userEntity2)
				.profile(profileEntity2)
				.build();

			userProfileRepository.save(userProfileEntity2);

		};

	}



}
