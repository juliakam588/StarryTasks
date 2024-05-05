package com.starrytasks.backend.service;

import com.starrytasks.backend.api.external.FamilyOverviewDTO;

public interface ParentService {
    FamilyOverviewDTO getFamilyOverview(Long parentId);
}
