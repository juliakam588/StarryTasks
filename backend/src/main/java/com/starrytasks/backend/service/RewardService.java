package com.starrytasks.backend.service;

import com.starrytasks.backend.api.external.ChildRewardsDTO;
import com.starrytasks.backend.api.external.RewardDTO;
import com.starrytasks.backend.api.internal.User;
import com.starrytasks.backend.api.internal.UserStars;

import java.util.List;

public interface RewardService {
    List<RewardDTO> getAllRewards();

    UserStars getAcquiredStars(Long id);

    void exchangeReward(User user, Long rewardId);

    void approveReward(Long userRewardId, Long parentId);

    void rejectReward(Long userRewardId, Long parentId);

    List<ChildRewardsDTO> getChildrenRewards(Long parentId);
}
