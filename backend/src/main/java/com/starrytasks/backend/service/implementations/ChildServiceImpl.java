package com.starrytasks.backend.service.implementations;


import com.starrytasks.backend.api.external.UserProfileDTO;
import com.starrytasks.backend.api.internal.UserProfile;
import com.starrytasks.backend.api.internal.UserStars;
import com.starrytasks.backend.mapper.UserProfileMapper;
import com.starrytasks.backend.repository.UserProfileRepository;
import com.starrytasks.backend.repository.UserStarsRepository;
import com.starrytasks.backend.service.ChildService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChildServiceImpl implements ChildService {
    private final UserProfileRepository userProfileRepository;
    private final UserStarsRepository userStarsRepository;
    private final UserProfileMapper userProfileMapper;

    public UserProfileDTO getChildProfile(Long childId) {
        UserProfile userProfile = userProfileRepository.findById(childId).orElse(null);
        if (userProfile != null) {
            return new UserProfileDTO()
                    .setId(userProfile.getId())
                    .setName(userProfile.getName());

        } else {
            return null;
        }
    }
}
