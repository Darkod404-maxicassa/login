package com.microservice.auth.microservice_auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.auth.microservice_auth.controller.request.CreateUserDTO;
import com.microservice.auth.microservice_auth.entity.ProfileEntity;
import com.microservice.auth.microservice_auth.entity.UserEntity;
import com.microservice.auth.microservice_auth.repository.CompanyRepository;
import com.microservice.auth.microservice_auth.repository.ProfileRepository;
import com.microservice.auth.microservice_auth.repository.UserRepository;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
public class PrincipalController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping("/test")
    public String hello() {
        return "== TODO FUNCIONANDO CORRECTAMENTE ==";
    }
    
    @GetMapping("/securedTest")
    public String helloSecured() {
        return "== 😁 TODO FUNCIONANDO SEGURO 😁 ==";
    }

    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserDTO createUserDTO) {
        
        Long profileId = createUserDTO.getProfile(); // Assuming getProfile() returns a single Long
        ProfileEntity profile = profileRepository.findById(profileId)
        .orElseThrow(() -> new RuntimeException("Profile ID " + profileId + " not found"));

        // Crear la entidad de usuario
        UserEntity userEntity = UserEntity.builder()
                .document(createUserDTO.getUsername())
                .username(createUserDTO.getUsername())
                .password(passwordEncoder.encode(createUserDTO.getPassword()))
                .company(companyRepository.findById(createUserDTO.getCompanyId()).get())
                .firstName(createUserDTO.getFirstName())
                .profile(profile)
                .build();
                
        // Guardar el usuario en la base de datos
        userRepository.save(userEntity);

        return ResponseEntity.ok(userEntity);
    }
    
    @DeleteMapping("/deleteUser")
    public String deleteUser  (@RequestParam String id){
        userRepository.deleteById(Long.parseLong(id));
        return "Se ha borrado el user con el id -> ".concat(id);
    }

}
