package com.starrytasks.backend.controller;

import com.starrytasks.backend.api.external.RewardDTO;
import com.starrytasks.backend.api.external.StatusResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rewards")
public class RewardController {

    @GetMapping
    public List<RewardDTO> getAllRewards() {
       return List.of(new RewardDTO()
               .setId(1L)
               .setName("Going later to sleep")
               .setCostInStars(60)
               .setDescription("")
       );
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