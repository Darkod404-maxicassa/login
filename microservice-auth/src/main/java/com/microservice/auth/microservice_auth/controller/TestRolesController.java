package com.microservice.auth.microservice_auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRolesController {

    @GetMapping("/accessAdmin")
    public String accessAdmin(){
        return "Hola, has accedido como ADMIN";
    }

    @GetMapping("/accessUser")
    public String accessUser(){
        return "Hola, has accedido como USER";
    }
    
    @GetMapping("/accessInvited")
    public String accessInvited(){
        return "Hola, has accedido como INVITED";
    }
}