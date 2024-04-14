package com.starrytasks.backend.controller;

import com.starrytasks.backend.api.external.StatusResponseDTO;
import com.starrytasks.backend.api.external.UserRewardDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/user-rewards")
public class UserRewardController {


    @GetMapping
    public List<UserRewardDTO> getAllUserRewards() {
        return List.of(new UserRewardDTO()
                .setRewardId(1L)
                .setUserId(1L)
                .setRedemptionDate(LocalDate.now().plusDays(30))
        );
    }

    @PostMapping
    public ResponseEntity<Object> grantRewardToUser(@RequestBody UserRewardDTO userRewardDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new StatusResponseDTO(201));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> revokeRewardFromUser(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(new StatusResponseDTO(200));
    }
}
