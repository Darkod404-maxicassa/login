package com.microservice.auth.microservice_auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.microservice.auth.microservice_auth.entity.CompanyEntity;
import com.microservice.auth.microservice_auth.entity.ProfileEntity;
import com.microservice.auth.microservice_auth.repository.CompanyRepository;
import com.microservice.auth.microservice_auth.repository.ProfileRepository;

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

	@Bean
	CommandLineRunner init(){

		return args -> {

			ProfileEntity profileEntity = ProfileEntity.builder()
				.name("USER")
				.build();

			profileRepository.save(profileEntity);

			profileEntity = ProfileEntity.builder()
				.name("ADMIN")
				.build();

			profileRepository.save(profileEntity);

			profileEntity = ProfileEntity.builder()
				.name("SUPERADMIN")
				.build();

			profileRepository.save(profileEntity);

			CompanyEntity companyEntity = CompanyEntity.builder()
				.name("PEGOMAX")
				
				.build();
			
			companyRepository.save(companyEntity);	

			companyEntity = CompanyEntity.builder()
				.name("TUCASSA")
				.build();
			
			companyRepository.save(companyEntity);

		};

	}

	

}
