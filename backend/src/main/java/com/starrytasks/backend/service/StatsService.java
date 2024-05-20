package com.starrytasks.backend.service;

import com.starrytasks.backend.api.external.StatsDTO;

public interface StatsService {
    StatsDTO getStatsForParent(Long parentId);
}
