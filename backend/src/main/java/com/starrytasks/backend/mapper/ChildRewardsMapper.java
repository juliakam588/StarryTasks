package com.starrytasks.backend.mapper;

import com.starrytasks.backend.api.external.ChildRewardsDTO;
import com.starrytasks.backend.api.external.RewardDTO;
import com.starrytasks.backend.api.internal.User;
import com.starrytasks.backend.api.internal.UserReward;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ChildRewardsMapper {
    private final RewardMapper rewardMapper;

    public ChildRewardsDTO map(User child, List<UserReward> rewards) {
        List<RewardDTO> rewardDTOs = rewards.stream()
                .map(rewardMapper::map)
                .collect(Collectors.toList());

        return new ChildRewardsDTO(child.getId(), child.getUserProfile().getName(), rewardDTOs);
    }
}
