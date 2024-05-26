package com.starrytasks.backend.mapper;

import com.starrytasks.backend.api.external.RewardDTO;
import com.starrytasks.backend.api.internal.Reward;
import com.starrytasks.backend.api.internal.UserReward;
import com.starrytasks.backend.service.FileUtils;
import org.springframework.stereotype.Component;

@Component
public class RewardMapper {
    public RewardDTO map(Reward reward) {
        return new RewardDTO()
                .setId(reward.getRewardId())
                .setCostInStars(reward.getDefaultCostInStars())
                .setName(reward.getName())
                .setImageUrl(FileUtils.readFileFromLocation(reward.getImageUrl()))
                .setIsDefault(reward.getIsDefault());
    }
    public RewardDTO map(UserReward userReward) {
        return new RewardDTO()
                .setId(userReward.getReward().getRewardId())
                .setName(userReward.getReward().getName())
                .setCostInStars(userReward.getReward().getDefaultCostInStars())
                .setImageUrl(FileUtils.readFileFromLocation(userReward.getReward().getImageUrl()))
                .setIsDefault(userReward.getReward().getIsDefault())
                .setIsGranted(userReward.isGranted())
                .setUserRewardId(userReward.getUserRewardId())
                .setRedemptionDate(userReward.getRedemptionDate());
    }

}
