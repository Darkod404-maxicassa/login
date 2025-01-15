package com.microservice.auth.microservice_auth.controller.request;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDTO {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private Long companyId;

    private Long profile;

    private String firstName;

}
