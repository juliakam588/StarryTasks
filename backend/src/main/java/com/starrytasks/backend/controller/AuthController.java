package com.starrytasks.backend.controller;

import com.starrytasks.backend.api.external.RoleDTO;
import com.starrytasks.backend.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    //private AuthService authService;

    private final RoleService roleService;

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
        return roleService.getAllRoles();

    }
    @PostMapping("/roles/select")
    public ResponseEntity<String> selectRole(@RequestBody RoleDTO roleDTO) {
        String role = roleDTO.getName();
        System.out.println("Role received: " + role);
        //TODO add logic of selecting roles
        return ResponseEntity.ok("Role selected: " + role);
    }

}