package com.starrytasks.backend.controller;

import com.starrytasks.backend.api.external.FamilyOverviewDTO;
import com.starrytasks.backend.api.external.UserProfileDTO;
import com.starrytasks.backend.api.internal.User;
import com.starrytasks.backend.repository.UserRepository;
import com.starrytasks.backend.service.ParentService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/parent")
@RequiredArgsConstructor
public class ParentController {

    private final ParentService parentService;
    private final UserRepository userRepository;

    @GetMapping("/overview")
    public FamilyOverviewDTO getFamilyOverview(Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        Long parentId = currentUser.getId();
        return parentService.getFamilyOverview(parentId);
    }

    @GetMapping("/children/{childId}")
    public ResponseEntity<?> getChildDetails(@PathVariable Long childId) {
        UserProfileDTO child = parentService.getChildDetails(childId);
        if (child != null) {
            return ResponseEntity.ok(child);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Child not found");
        }
    }

}
