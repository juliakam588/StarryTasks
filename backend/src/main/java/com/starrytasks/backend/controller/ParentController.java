package com.starrytasks.backend.controller;

import com.starrytasks.backend.api.external.ChildRewardsDTO;
import com.starrytasks.backend.api.external.FamilyOverviewDTO;
import com.starrytasks.backend.api.external.UserProfileDTO;
import com.starrytasks.backend.api.internal.User;
import com.starrytasks.backend.repository.UserRepository;
import com.starrytasks.backend.service.ParentService;
import com.starrytasks.backend.service.RewardService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/parent")
@RequiredArgsConstructor
public class ParentController {

    private final ParentService parentService;
    private final UserRepository userRepository;
    private final RewardService rewardService;

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
    @GetMapping("/rewards")
    public ResponseEntity<?> getChildrenRewards(Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        Long parentId = currentUser.getId();
        List<ChildRewardsDTO> childrenRewards = rewardService.getChildrenRewards(parentId);
        return ResponseEntity.ok(childrenRewards);
    }
}
