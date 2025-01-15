// package com.microservice.auth.microservice_auth.security.filter;

// import java.io.IOException;
// import java.util.HashMap;
// import java.util.Map;

// import org.springframework.http.HttpStatus;
// import org.springframework.http.MediaType;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.AuthenticationException;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// import com.fasterxml.jackson.core.exc.StreamReadException;
// import com.fasterxml.jackson.databind.DatabindException;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.microservice.auth.microservice_auth.entity.UserEntity;

// import com.microservice.auth.microservice_auth.security.jwt.JwtUtils;

// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;

// public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

//     private final JwtUtils jwtUtils;

//     public JwtAuthenticationFilter(JwtUtils jwtUtils) {
//         this.jwtUtils = jwtUtils;
//     }


//     @Override
//     public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        
//         UserEntity userEntity = null;
//         String username = "";
//         String password = "";

//         try {
//             userEntity = new ObjectMapper().readValue(request.getInputStream(), UserEntity.class);
//             username = userEntity.getUsername();
//             password = userEntity.getPassword();
//         } catch (StreamReadException e) {
//             throw new RuntimeException(e);
//         } catch (DatabindException e){
//             throw new RuntimeException(e);
//         } catch (IOException e) {
//             throw new RuntimeException(e);
//         }

//         UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
//         return getAuthenticationManager().authenticate(authenticationToken);
//     }

//     @Override
//     protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        
//         User user = (User) authResult.getPrincipal();
//         String token = jwtUtils.generatedAccesToken(user.getUsername());

//         response.addHeader("Authorization", token);

//         Map<String, Object> httResponse = new HashMap<>();
//         httResponse.put("token", token);
//         httResponse.put("Message","Autenticacion Correcta");
//         httResponse.put("Username",user.getUsername());

//         response.getWriter().write(new ObjectMapper().writeValueAsString(httResponse));  
//         response.setStatus(HttpStatus.OK.value());
//         response.setContentType(MediaType.APPLICATION_JSON_VALUE); 
//         response.getWriter().flush();


//         super.successfulAuthentication(request, response, chain, authResult);
//     }

    

// }


package com.microservice.auth.microservice_auth.security.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.auth.microservice_auth.entity.UserEntity;
import com.microservice.auth.microservice_auth.repository.ProfileApplicationRoleRepository;
import com.microservice.auth.microservice_auth.repository.UserRepository;
import com.microservice.auth.microservice_auth.security.jwt.JwtUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private ProfileApplicationRoleRepository profileApplicationRoleRepository;

    public JwtAuthenticationFilter(JwtUtils jwtUtils, UserRepository userRepository) {
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UserEntity userEntity = null;
        String username = "";
        String password = "";

        try {
            userEntity = new ObjectMapper().readValue(request.getInputStream(), UserEntity.class);
            username = userEntity.getUsername();
            password = userEntity.getPassword();
        } catch (IOException e) {
            throw new RuntimeException("Error parsing login request", e);
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        return getAuthenticationManager().authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        try {
  
            User user = (User) authResult.getPrincipal();


            String token = jwtUtils.generatedAccesToken(user.getUsername());

            UserEntity userEntity = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

            Map<String, Object> companyData = new HashMap<>();
            if (userEntity.getCompany() != null) {
                companyData.put("id", userEntity.getCompany().getId());
                companyData.put("name", userEntity.getCompany().getName());
            }

            Map<String, Object> profileData = new HashMap<>();
            if (userEntity.getCompany() != null) {
                profileData.put("id", userEntity.getProfile().getId());
                profileData.put("name", userEntity.getProfile().getName());
            }


            //System.out.println(profileApplicationRoleRepository.findApplicationsAndRolesByProfileId(userEntity.getProfile().getId()));



            Map<String, Object> httpResponse = new HashMap<>();
            
            httpResponse.put("token", token);
            
            httpResponse.put("username", user.getUsername());
            httpResponse.put("firstName", userEntity.getFirstName());            
            httpResponse.put("company", companyData);

            httpResponse.put("Profile", profileData);
            httpResponse.put("Accesos", profileData);

            // Configurar la respuesta HTTP
            response.setStatus(HttpStatus.OK.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(new ObjectMapper().writeValueAsString(httpResponse));
            response.getWriter().flush();
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
            response.getWriter().flush();
        }
    }
}
