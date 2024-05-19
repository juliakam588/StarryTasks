package com.starrytasks.backend.controller;

import com.starrytasks.backend.api.external.RewardDTO;
import com.starrytasks.backend.api.external.StatusResponseDTO;
import com.starrytasks.backend.api.internal.User;
import com.starrytasks.backend.api.internal.UserStars;
import com.starrytasks.backend.service.ParentService;
import com.starrytasks.backend.service.RewardService;
import com.starrytasks.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/rewards")
public class RewardController {

    private final RewardService rewardService;
    private final ParentService parentService;

    @GetMapping
    public List<RewardDTO> getAllRewards() {
        return rewardService.getAllRewards();
    }

    @GetMapping("/stars")
    public ResponseEntity<?> getStars(@RequestParam Optional<Long> childId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        if (user.getRole().getName().equals("Parent") && childId.isPresent()) {
            if (!parentService.isParentOfChild(user.getId(), childId.get())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to view this information");
            }
            UserStars userStars = rewardService.getAcquiredStars(childId.get());
            return ResponseEntity.ok(userStars.getTotalStars());
        } else {
            UserStars userStars = rewardService.getAcquiredStars(user.getId());
            return ResponseEntity.ok(userStars.getTotalStars());
        }
    }

    @PostMapping("/exchange")
    public ResponseEntity<?> exchangeReward(@RequestParam Long rewardId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        try {
            rewardService.exchangeReward(user, rewardId);
            return ResponseEntity.ok().body("Reward exchanged successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error exchanging reward: " + e.getMessage());
        }
    }

    @PostMapping("/approve")
    public ResponseEntity<?> approveReward(@RequestParam Long userRewardId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        try {
            rewardService.approveReward(userRewardId, user.getId());
            return ResponseEntity.ok("Reward approved successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to approve reward");
        }
    }

    @PostMapping("/reject")
    public ResponseEntity<?> rejectReward(@RequestParam Long userRewardId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        try {
            rewardService.rejectReward(userRewardId, user.getId());
            return ResponseEntity.ok("Reward rejected successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to reject reward");
        }
    }
    @PostMapping
    public ResponseEntity<Object> addReward(@RequestBody RewardDTO rewardDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new StatusResponseDTO(201));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> editCategory(@PathVariable Long id, @RequestBody RewardDTO rewardDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(new StatusResponseDTO(200));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCategory(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(new StatusResponseDTO(200));
    }
}