package com.starrytasks.backend.service;

import com.starrytasks.backend.api.external.ChildRewardsDTO;
import com.starrytasks.backend.api.external.RewardDTO;
import com.starrytasks.backend.api.internal.User;
import com.starrytasks.backend.api.internal.UserStars;

import java.util.List;

public interface RewardService {

    UserStars getAcquiredStars(Long id);

    void exchangeReward(User user, Long rewardId);

    void approveReward(Long userRewardId, Long parentId);

    void rejectReward(Long userRewardId, Long parentId);

    List<ChildRewardsDTO> getChildrenRewards(Long parentId);

    List<RewardDTO> getAllRewards(Long userId, String role);

    void updateCustomCostInStars(Long parentId, Long rewardId, Integer customCostInStars);

    void deleteUserReward(Long userRewardId);
}
