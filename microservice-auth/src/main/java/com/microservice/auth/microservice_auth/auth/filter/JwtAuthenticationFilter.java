package com.microservice.auth.microservice_auth.auth.filter;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import static com.microservice.auth.microservice_auth.auth.TokenJwtConfig.CONTENT_TYPE;
import static com.microservice.auth.microservice_auth.auth.TokenJwtConfig.HEADER_AUTHORIZATION;
import static com.microservice.auth.microservice_auth.auth.TokenJwtConfig.PREFIX_TOKEN;
import static com.microservice.auth.microservice_auth.auth.TokenJwtConfig.SECRET_KEY;
import com.microservice.auth.microservice_auth.entity.UserEntity;
import com.microservice.auth.microservice_auth.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        String username = null;
        String password = null;

        try {
            UserEntity user = new ObjectMapper().readValue(request.getInputStream(), UserEntity.class);
            username = user.getUsername();
            password = user.getPassword();
        } catch (StreamReadException e) {
            e.printStackTrace();
        } catch (DatabindException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
                password);
        return this.authenticationManager.authenticate(authenticationToken);
    }

    // @Override
    // protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
    //         Authentication authResult) throws IOException, ServletException {

    //     org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authResult
    //             .getPrincipal();
    //     String username = user.getUsername();
    //     Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();
    //     boolean isAdmin = roles.stream().anyMatch(role -> role.getAuthority().equals("PERFIL_ADMIN"));
    //     Claims claims = Jwts
    //             .claims()
    //             .add("authorities", new ObjectMapper().writeValueAsString(roles))
    //             .add("username", username)
    //             .add("isAdmin", isAdmin)
    //             .build();

    //     String jwt = Jwts.builder()
    //             .subject(username)
    //             .claims(claims)
    //             .signWith(SECRET_KEY)
    //             .issuedAt(new Date())
    //             .expiration(new Date(System.currentTimeMillis() + 3600000))
    //             .compact();

    //     response.addHeader(HEADER_AUTHORIZATION, PREFIX_TOKEN + jwt);

    //     Map<String, String> body = new HashMap<>();
    //     body.put("token", jwt);
    //     body.put("username", username);
    //     body.put("message", String.format("Hola %s has iniciado sesion con exito", username));

    //     response.getWriter().write(new ObjectMapper().writeValueAsString(body));
    //     response.setContentType(CONTENT_TYPE);
    //     response.setStatus(200);
    // }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {

        // Convertimos el principal a la clase UserEntity para acceder al documento
        org.springframework.security.core.userdetails.User userDetails = 
            (org.springframework.security.core.userdetails.User) authResult.getPrincipal();

        // Obtener el documento del usuario desde el contexto de autenticación o una fuente confiable
        String username = userDetails.getUsername();
        String document = ""; // Inicializamos como vacío

        if (authResult.getDetails() instanceof UserEntity) {
            UserEntity userEntity = (UserEntity) authResult.getDetails();
            document = userEntity.getDocument();
        }

        Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();
        boolean isAdmin = roles.stream().anyMatch(role -> role.getAuthority().equals("PERFIL_ADMIN"));

        Claims claims = Jwts
                .claims()
                .add("authorities", new ObjectMapper().writeValueAsString(roles))
                .add("username", username)
                .add("isAdmin", isAdmin)
                .add("document", document)
                .build();

        String jwt = Jwts.builder()
                .subject(username)
                .claims(claims)
                .signWith(SECRET_KEY)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 3600000))
                .compact();

        response.addHeader(HEADER_AUTHORIZATION, PREFIX_TOKEN + jwt);

        Map<String, String> body = new HashMap<>();
        body.put("token", jwt);
        body.put("username", username);
        body.put("document", document);
        body.put("message", String.format("Hola %s has iniciado sesion con exito", username));

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setContentType(CONTENT_TYPE);
        response.setStatus(200);
    }


    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {

        Map<String, String> body = new HashMap<>();
        body.put("message", "Error en la autenticacion con username o password incorrecto!");
        body.put("error", failed.getMessage());

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setContentType(CONTENT_TYPE);
        response.setStatus(401);
    }

}
