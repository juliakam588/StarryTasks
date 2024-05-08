package com.starrytasks.backend.mapper;

import com.starrytasks.backend.api.external.UserProfileDTO;
import com.starrytasks.backend.api.internal.UserProfile;
import com.starrytasks.backend.api.internal.UserStars;
import com.starrytasks.backend.service.FileUtils;
import org.springframework.stereotype.Component;

@Component
public class UserProfileMapper {

    public UserProfileDTO map(UserProfile userProfile, UserStars userStars, Long childId) {
        UserProfileDTO dto = new UserProfileDTO();
        dto.setId(childId);
        dto.setName(userProfile.getName());
        dto.setProfilePicture(FileUtils.readFileFromLocation(userProfile.getProfilePicturePath()));
        if (userStars != null) {
            dto.setCurrentStars(userStars.getCurrentStars());
        }
        return dto;
    }
}
