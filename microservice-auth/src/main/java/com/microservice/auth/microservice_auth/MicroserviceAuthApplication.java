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
import com.microservice.auth.microservice_auth.repository.ApplicationGroupRepository;
import com.microservice.auth.microservice_auth.repository.ApplicationRepository;
import com.microservice.auth.microservice_auth.repository.CompanyRepository;
import com.microservice.auth.microservice_auth.repository.ProfileApplicationRoleRepository;
import com.microservice.auth.microservice_auth.repository.ProfileRepository;
import com.microservice.auth.microservice_auth.repository.RoleRepository;

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

	

	@Bean
	CommandLineRunner init(){

		return args -> {

			//! PERFILES

			ProfileEntity profileEntity1 = ProfileEntity.builder()
				.name("Desarrollador Junior")
				.build();

			profileRepository.save(profileEntity1);

			ProfileEntity profileEntity2 = ProfileEntity.builder()
				.name("Desarrollador SemiSenior")
				.build();

			profileRepository.save(profileEntity2);

			ProfileEntity profileEntity3 = ProfileEntity.builder()
				.name("Desarrollador Senior")
				.build();

			profileRepository.save(profileEntity3);

			
			
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


			//! PERFILES_APLICACIONES_ROLES

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
				.profile(profileEntity3)
				.application(applicationEntity2)
				.role(roleEntity3)
				.build();

				profileApplicationRoleRepository.save(applicationRoleEntity);

		};

	}

	

}
