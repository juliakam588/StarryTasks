package com.starrytasks.backend.controller;

import com.starrytasks.backend.api.external.UserProfileDTO;
import com.starrytasks.backend.api.internal.User;
import com.starrytasks.backend.service.ChildService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/child")
@RequiredArgsConstructor
public class ChildController {

    private final ChildService childService;

    @GetMapping("/profile")
    public ResponseEntity<UserProfileDTO> getChildProfile(Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        Long childId = currentUser.getId();
        UserProfileDTO userProfile = childService.getChildProfile(childId);
        if (userProfile != null) {
            return ResponseEntity.ok(userProfile);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}