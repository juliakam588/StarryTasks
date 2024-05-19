package com.starrytasks.backend.mapper;

import com.starrytasks.backend.api.external.RewardDTO;
import com.starrytasks.backend.api.internal.Reward;
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

}
