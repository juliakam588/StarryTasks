package com.starrytasks.backend.controller;

import com.starrytasks.backend.api.external.UserStarsDTO;
import com.starrytasks.backend.api.external.WeeklyStatsFunction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stats")
public class StatsController {


    @GetMapping("/stars/{userId}")
    public UserStarsDTO getUserStars(@PathVariable Long userId) {
        UserStarsDTO sampleUserStars = new UserStarsDTO()
                .setUserId(userId)
                .setTotalStars(120)
                .setStarsSpent(50);
        sampleUserStars.setCurrentStars(sampleUserStars.getTotalStars() - sampleUserStars.getStarsSpent());

        return sampleUserStars;
    }

}
