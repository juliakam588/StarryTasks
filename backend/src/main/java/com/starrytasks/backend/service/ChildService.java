package com.starrytasks.backend.service;

import com.starrytasks.backend.api.external.UserProfileDTO;

public interface ChildService {
    UserProfileDTO getChildProfile(Long childId);
}
