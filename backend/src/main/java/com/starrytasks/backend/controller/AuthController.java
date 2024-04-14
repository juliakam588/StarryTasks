package com.starrytasks.backend.controller;

import com.starrytasks.backend.api.external.RoleDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    //private AuthService authService;

    @PostMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/verify")
    public String verify() {
        return "verify";
    }
    @GetMapping("/roles")
    public List<RoleDTO> getAllRoles() {
        return List.of(new RoleDTO()
                .setId(1L)
                .setName("Child")
        );

    }


}