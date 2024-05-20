package com.starrytasks.backend.api.external;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Data
@RequiredArgsConstructor
public class StatsDTO {
    private List<Map<String, Integer>> weeklyStars;
    private List<Map<String, Integer>> weeklyIncompleteTasks;

    public StatsDTO(List<Map<String, Integer>> weeklyStars, List<Map<String, Integer>> weeklyIncompleteTasks) {
        this.weeklyStars = weeklyStars;
        this.weeklyIncompleteTasks = weeklyIncompleteTasks;
    }
}

