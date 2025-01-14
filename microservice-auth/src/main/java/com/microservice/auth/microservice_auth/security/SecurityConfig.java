package com.microservice.auth.microservice_auth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.microservice.auth.microservice_auth.repository.UserRepository;
import com.microservice.auth.microservice_auth.security.filter.JwtAuthenticationFilter;
import com.microservice.auth.microservice_auth.security.filter.JwtAuthorizationFilter;
import com.microservice.auth.microservice_auth.security.jwt.JwtUtils;
import com.microservice.auth.microservice_auth.service.UserDetailsServiceImpl;


// @SuppressWarnings("deprecation")
// @Configuration
// @EnableGlobalMethodSecurity(prePostEnabled = true)
// public class SecurityConfig {

//     @Autowired
//     JwtUtils jwtUtils;

//     @Autowired
//     UserDetailsServiceImpl userDetailsService;

//     @Autowired
//     JwtAuthorizationFilter jwtAuthorizationFilter; 

//     @Bean
//     @SuppressWarnings("unused")
//      SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager) throws Exception {

//         JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtils);
//         jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);
//         jwtAuthenticationFilter.setFilterProcessesUrl("/login"); // Ruta para el login

//         return httpSecurity
//                 .csrf(csrf -> csrf.disable())
//                 .authorizeHttpRequests(auth -> {
//                     auth.requestMatchers("/test").permitAll();
//                     auth.requestMatchers("/createUser").permitAll();
//                     auth.anyRequest().authenticated();
//                 })
//                 .sessionManagement(session -> {
//                     session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//                 })
//                 .addFilter(jwtAuthenticationFilter)
//                 .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
//                 .build();
//     }

//     @Bean
//     @SuppressWarnings("unused")
//     PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }

//     @Bean
//     @SuppressWarnings({ "unused", "removal" })
//     AuthenticationManager authenticationManager(HttpSecurity httpSecurity, PasswordEncoder passwordEncoder) throws Exception {
//         return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
//                 .userDetailsService(userDetailsService)
//                 .passwordEncoder(passwordEncoder)
//                 .and().build();
//     }

//     // public static void main(String[] args) {
//     //     System.out.println("PASSWORD == " + new BCryptPasswordEncoder().encode("123"));
//     // }

// }


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtAuthorizationFilter jwtAuthorizationFilter;

    @Autowired
    private UserRepository userRepository; // Agregar UserRepository

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager) throws Exception {

        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtils, userRepository);
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);
        jwtAuthenticationFilter.setFilterProcessesUrl("/login");

        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/test").permitAll();
                    auth.requestMatchers("/createUser").permitAll();
                    auth.anyRequest().authenticated();
                })
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .addFilter(jwtAuthenticationFilter)
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(HttpSecurity httpSecurity, PasswordEncoder passwordEncoder) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder)
                .and().build();
    }
}
