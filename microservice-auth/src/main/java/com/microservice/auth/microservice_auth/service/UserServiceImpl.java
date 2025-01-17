package com.microservice.auth.microservice_auth.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microservice.auth.microservice_auth.entity.ProfileEntity;
import com.microservice.auth.microservice_auth.entity.UserEntity;
import com.microservice.auth.microservice_auth.entity.models.IUser;
import com.microservice.auth.microservice_auth.entity.models.UserRequest;
import com.microservice.auth.microservice_auth.repository.ProfileRepository;
import com.microservice.auth.microservice_auth.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;
    private ProfileRepository profileRepository;

    private PasswordEncoder passwordEncoder;
    
    
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, ProfileRepository profileRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.profileRepository = profileRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserEntity> findAll() {
        return (List) this.userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserEntity> findAll(Pageable pageable) {
        return this.userRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<UserEntity> findById(@NonNull Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    @Override
    public UserEntity save(UserEntity user) {
        user.setProfiles(getProfiles(user));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    
    @Override
    @Transactional
    public Optional<UserEntity> update(UserRequest user, Long id) {
        Optional<UserEntity> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            UserEntity userDb = userOptional.get();
            userDb.setEmail(user.getEmail());
            userDb.setFirstLastName(user.getLastname());
            userDb.setFirstName(user.getName());
            userDb.setUsername(user.getUsername());

            userDb.setProfiles(getProfiles(user));
            return Optional.of(userRepository.save(userDb));
        }
        return Optional.empty();
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    private List<ProfileEntity> getProfiles(IUser user) {
        List<ProfileEntity> roles = new ArrayList<>();
        Optional<ProfileEntity> optionalRoleUser = profileRepository.findByName("PROFILE_USER");
        optionalRoleUser.ifPresent(roles::add);

        if (user.isAdmin()) {
            Optional<ProfileEntity> optionalRoleAdmin = profileRepository.findByName("PROFILE_ADMIN");
            optionalRoleAdmin.ifPresent(roles::add);
        }
        return roles;
    }

    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

}
