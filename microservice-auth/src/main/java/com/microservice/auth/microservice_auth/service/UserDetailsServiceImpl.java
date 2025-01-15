package com.microservice.auth.microservice_auth.service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.microservice.auth.microservice_auth.entity.ProfileEntity;
import com.microservice.auth.microservice_auth.entity.UserEntity;
import com.microservice.auth.microservice_auth.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String document) throws UsernameNotFoundException {
        
        // Buscar al usuario por su documento
        UserEntity userEntity = userRepository.findByUsername(document)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario " + document + " no existe."));

        // Obtener el perfil asociado al usuario
        ProfileEntity profile = userEntity.getProfile();

        if (profile == null) {
            throw new UsernameNotFoundException("El usuario " + document + " no tiene un perfil asociado.");
        }

        // Crear las autoridades con el perfil del usuario
        Collection<? extends GrantedAuthority> authorities = Set.of(
                new SimpleGrantedAuthority("ROLE_".concat(profile.getName()))
        );

        // Crear y retornar el objeto UserDetails
        return new User(
                userEntity.getUsername(),
                userEntity.getPassword(),
                true,  // accountNonExpired
                true,  // credentialsNonExpired
                true,  // accountNonLocked
                true,  // enabled
                authorities
        );
    }
}