package com.starrytasks.backend.mapper;

import com.starrytasks.backend.api.external.UserProfileDTO;
import com.starrytasks.backend.api.internal.UserProfile;
import com.starrytasks.backend.api.internal.UserStars;
import org.springframework.stereotype.Component;

@Component
public class UserProfileMapper {

    public UserProfileDTO map(UserProfile userProfile, UserStars userStars) {
        UserProfileDTO dto = new UserProfileDTO();
        dto.setName(userProfile.getName());
        dto.setProfilePictureUrl(userProfile.getProfilePicturePath());
        if (userStars != null) {
            dto.setCurrentStars(userStars.getCurrentStars());
        }
        return dto;
    }
}
