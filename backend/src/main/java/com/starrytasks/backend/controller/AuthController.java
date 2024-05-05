package com.starrytasks.backend.controller;

import com.starrytasks.backend.api.external.*;
import com.starrytasks.backend.api.internal.Role;
import com.starrytasks.backend.api.internal.User;
import com.starrytasks.backend.service.AuthenticationService;
import com.starrytasks.backend.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final AuthenticationService authenticationService;

    private final RoleService roleService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }


    @PostMapping("/verify")
    public ResponseEntity<UserDTO> verify() {
        return ResponseEntity.ok(authenticationService.verify());
    }
    @GetMapping("/roles")
    public List<RoleDTO> getAllRoles() {
        return roleService.getAllRoles();

    }
    @PostMapping("/roles/select")
    public ResponseEntity<AuthenticationResponse> selectRole(@RequestBody RoleDTO request, @AuthenticationPrincipal User currentUser) {
        if (currentUser == null) {
            throw new IllegalStateException("User is not authenticated");
        }
        String jwt = roleService.assignRoleAndGenerateToken(request.getName(), currentUser);
        return ResponseEntity.ok(new AuthenticationResponse(jwt, true));
    }

    @GetMapping("/user-details")
    public ResponseEntity<UserDetailsDTO> getUserDetails(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(roleService.getAdditionalUserDetails(user));
    }

}