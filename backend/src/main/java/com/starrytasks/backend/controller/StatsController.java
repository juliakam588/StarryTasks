package com.starrytasks.backend.controller;

import com.starrytasks.backend.api.external.StatsDTO;
import com.starrytasks.backend.api.internal.User;
import com.starrytasks.backend.service.StatsService;
import com.starrytasks.backend.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stats")
@RequiredArgsConstructor
public class StatsController {

    private final StatsService statsService;

    @GetMapping
    public ResponseEntity<StatsDTO> getStats(Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        StatsDTO stats = statsService.getStatsForParent(currentUser.getId());
        return ResponseEntity.ok(stats);
    }



}
