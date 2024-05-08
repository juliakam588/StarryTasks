package com.starrytasks.backend.service;

import com.starrytasks.backend.api.external.FamilyOverviewDTO;
import com.starrytasks.backend.api.external.UserProfileDTO;
import com.starrytasks.backend.api.internal.User;

public interface ParentService {
    FamilyOverviewDTO getFamilyOverview(Long parentId);

    UserProfileDTO getChildDetails(Long childId);
}
