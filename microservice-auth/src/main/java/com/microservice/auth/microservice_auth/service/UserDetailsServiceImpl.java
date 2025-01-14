package com.microservice.auth.microservice_auth.service;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.microservice.auth.microservice_auth.entity.UserEntity;
import com.microservice.auth.microservice_auth.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Override
public UserDetails loadUserByUsername(String documnent) throws UsernameNotFoundException {
    
    UserEntity userEntity = userRepository.findByUsername(documnent)
                                           .orElseThrow(() -> new UsernameNotFoundException("El usuario " + documnent + " no existe."));

    // Si userEntity.getRole() devuelve directamente una lista de nombres de roles
    Collection<? extends GrantedAuthority> authorities = userEntity.getProfiles()
            .stream()
            .map(roleName -> new SimpleGrantedAuthority("ROLE_".concat(roleName.getName())))
            .collect(Collectors.toSet());

    return new User(userEntity.getUsername(),
                    userEntity.getPassword(),
                    true, // accountNonExpired
                    true, // credentialsNonExpired
                    true, // accountNonLocked
                    true, // enabled
                    authorities);
}

}
