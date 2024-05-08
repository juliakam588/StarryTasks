package com.starrytasks.backend.service.implementations;

import com.starrytasks.backend.api.external.FamilyOverviewDTO;
import com.starrytasks.backend.api.external.UserProfileDTO;
import com.starrytasks.backend.api.internal.User;
import com.starrytasks.backend.api.internal.UserProfile;
import com.starrytasks.backend.api.internal.UserStars;
import com.starrytasks.backend.mapper.UserProfileMapper;
import com.starrytasks.backend.repository.UserProfileRepository;
import com.starrytasks.backend.repository.UserRepository;
import com.starrytasks.backend.repository.UserStarsRepository;
import com.starrytasks.backend.service.ParentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParentServiceImpl implements ParentService {

    private final UserRepository userRepository;
    private final UserProfileMapper userProfileMapper;
    private final UserProfileRepository userProfileRepository;
    private final UserStarsRepository userStarsRepository;


    @Override
    public FamilyOverviewDTO getFamilyOverview(Long parentId) {
        User parent = userRepository.findById(parentId).orElseThrow();
        List<User> children = userRepository.findByParentId(parentId);

        List<UserProfileDTO> childrenDTOs = children.stream()
                .map(child -> {

                    UserProfile profile = userProfileRepository.findByUserId(child.getId());
                    UserStars stars = userStarsRepository.findByUserId(child.getId());
                    return userProfileMapper.map(profile, stars, child.getId());
                })
                .collect(Collectors.toList());

        FamilyOverviewDTO familyOverviewDTO = new FamilyOverviewDTO();
        familyOverviewDTO.setParentName(parent.getUserProfile().getName());
        familyOverviewDTO.setChildren(childrenDTOs);

        return familyOverviewDTO;
    }

    @Override
    public UserProfileDTO getChildDetails(Long childId) {
        UserProfile profile = userProfileRepository.findByUserId(childId);
        UserStars stars = userStarsRepository.findByUserId(childId);
        return userProfileMapper.map(profile, stars, childId);
    }
}
