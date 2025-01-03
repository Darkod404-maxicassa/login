package com.microservice.auth.microservice_auth;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.microservice.auth.microservice_auth.entity.ERole;
import com.microservice.auth.microservice_auth.entity.RoleEntity;
import com.microservice.auth.microservice_auth.entity.UserEntity;
import com.microservice.auth.microservice_auth.repository.UserRepository;

@SpringBootApplication
public class MicroserviceAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceAuthApplication.class, args);
	}

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Bean
	CommandLineRunner init(){

		return args -> {

			UserEntity userEntity = UserEntity.builder()
				.email("elsanti@gmail.com")
				.username("elsanti")
				.password(passwordEncoder.encode("1234"))
				.camello("Hola")
				.roles(Set.of(RoleEntity.builder()
							.name(ERole.valueOf(ERole.ADMIN.name()))
							.build()))
				.build();

			UserEntity userEntity2 = UserEntity.builder()
				.email("elsanti2@gmail.com")
				.username("elsanti2")
				.password(passwordEncoder.encode("1234"))
				.camello("Hola")
				.roles(Set.of(RoleEntity.builder()
							.name(ERole.valueOf(ERole.USER.name()))
							.build()))
				.build();


			UserEntity userEntity3 = UserEntity.builder()
				.email("elsanti3@gmail.com")
				.username("elsanti3")
				.password(passwordEncoder.encode("1234"))
				.camello("Hola")
				.roles(Set.of(RoleEntity.builder()
							.name(ERole.valueOf(ERole.INVITED.name()))
							.build()))
				.build();


			userRepository.save(userEntity);
			userRepository.save(userEntity2);
			userRepository.save(userEntity3);

		};

	}

}
