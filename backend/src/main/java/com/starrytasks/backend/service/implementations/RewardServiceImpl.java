package com.starrytasks.backend.service.implementations;

import com.starrytasks.backend.api.external.ChildRewardsDTO;
import com.starrytasks.backend.api.external.RewardDTO;
import com.starrytasks.backend.api.internal.Reward;
import com.starrytasks.backend.api.internal.User;
import com.starrytasks.backend.api.internal.UserReward;
import com.starrytasks.backend.api.internal.UserStars;
import com.starrytasks.backend.mapper.RewardMapper;
import com.starrytasks.backend.repository.RewardRepository;
import com.starrytasks.backend.repository.UserRewardRepository;
import com.starrytasks.backend.repository.UserStarsRepository;
import com.starrytasks.backend.service.FileUtils;
import com.starrytasks.backend.service.ParentService;
import com.starrytasks.backend.service.RewardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RewardServiceImpl implements RewardService {

    private final RewardRepository rewardRepository;
    private final UserRewardRepository userRewardRepository;
    private final RewardMapper rewardMapper;
    private final UserStarsRepository userStarsRepository;
    private final ParentService parentService;


    @Override
    public List<RewardDTO> getAllRewards() {
        List<RewardDTO> rewardDTOs = rewardRepository.findAll().stream()
                .filter(Reward::getIsDefault)
                .map(rewardMapper::map)
                .collect(Collectors.toList());
        return rewardDTOs;
    }

    @Override
    public UserStars getAcquiredStars(Long id) {
        return userStarsRepository.findById(id).orElse(null);
    }

    @Override
    public void exchangeReward(User user, Long rewardId) {
        UserStars userStars = userStarsRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Reward reward = rewardRepository.findById(rewardId)
                .orElseThrow(() -> new IllegalArgumentException("Reward not found"));

        if (userStars.getTotalStars() < reward.getDefaultCostInStars()) {
            throw new IllegalStateException("Not enough stars to exchange for this reward");
        }

        userStars.setTotalStars(userStars.getTotalStars() - reward.getDefaultCostInStars());
        userStars.setStarsSpent(userStars.getStarsSpent() + reward.getDefaultCostInStars());
        userStarsRepository.save(userStars);

        UserReward userReward = new UserReward();
        userReward.setUser(user);
        userReward.setReward(reward);
        userReward.setRedemptionDate(LocalDate.now());
        userReward.setGranted(false);
        userRewardRepository.save(userReward);
    }
    @Override
    public void approveReward(Long userRewardId, Long parentId) {
        UserReward userReward = userRewardRepository.findById(userRewardId)
                .orElseThrow(() -> new RuntimeException("User reward not found"));
        if (!parentService.isParentOfChild(parentId, userReward.getUser().getId())) {
            throw new RuntimeException("Parent is not authorized to approve this reward");
        }
        userReward.setGranted(true);
        userRewardRepository.save(userReward);
    }

    @Override
    public void rejectReward(Long userRewardId, Long parentId) {
        UserReward userReward = userRewardRepository.findById(userRewardId)
                .orElseThrow(() -> new RuntimeException("User reward not found"));
        if (!parentService.isParentOfChild(parentId, userReward.getUser().getId())) {
            throw new RuntimeException("Parent is not authorized to reject this reward");
        }

        UserStars userStars = userStarsRepository.findByUserId(userReward.getUser().getId());
        userStars.setTotalStars(userStars.getTotalStars() + userReward.getReward().getDefaultCostInStars());
        userStarsRepository.save(userStars);

        userRewardRepository.delete(userReward);
    }

    @Override
    public List<ChildRewardsDTO> getChildrenRewards(Long parentId) {
        List<User> children = parentService.getChildrenOfParent(parentId);

        return children.stream().map(child -> {
            List<UserReward> rewards = userRewardRepository.findByUser(child);
            List<RewardDTO> rewardDTOs = rewards.stream().map(userReward -> new RewardDTO()
                            .setId(userReward.getReward().getRewardId())
                            .setName(userReward.getReward().getName())
                            .setCostInStars(userReward.getReward().getDefaultCostInStars())
                            .setImageUrl(FileUtils.readFileFromLocation(userReward.getReward().getImageUrl()))
                            .setIsDefault(userReward.getReward().getIsDefault())
                            .setIsGranted(userReward.isGranted())
                            .setUserRewardId(userReward.getUserRewardId())
                            .setRedemptionDate(userReward.getRedemptionDate()))
                    .collect(Collectors.toList());

            return new ChildRewardsDTO(child.getId(), child.getUserProfile().getName(), rewardDTOs);
        }).collect(Collectors.toList());
    }

}
