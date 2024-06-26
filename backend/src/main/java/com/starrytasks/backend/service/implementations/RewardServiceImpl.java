package com.starrytasks.backend.service.implementations;

import com.starrytasks.backend.api.external.ChildRewardsDTO;
import com.starrytasks.backend.api.external.RewardDTO;
import com.starrytasks.backend.api.internal.*;
import com.starrytasks.backend.mapper.ChildRewardsMapper;
import com.starrytasks.backend.mapper.RewardMapper;
import com.starrytasks.backend.repository.*;
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
    private final ParentRewardCostRepository parentRewardCostRepository;
    private final UserRepository userRepository;
    private final ChildRewardsMapper childRewardsMapper;


    @Override
    public List<RewardDTO> getAllRewards(Long userId, String role) {
        List<Reward> rewards = rewardRepository.findAll();
        if (!"Parent".equals(role)) {
            User user = userRepository.findUserById(userId);
            userId = user.getParent().getId();
        }
        List<ParentRewardCost> customCosts = parentRewardCostRepository.findByParentId(userId);

        return rewards.stream().map(reward -> {
                RewardDTO dto = rewardMapper.map(reward);
                customCosts.stream()
                        .filter(cost -> cost.getReward().getRewardId().equals(reward.getRewardId()))
                        .findFirst()
                        .ifPresent(cost -> dto.setCostInStars(cost.getCustomCostInStars()));
                return dto;
            }).collect(Collectors.toList());

    }


    @Override
    public void updateCustomCostInStars(Long parentId, Long rewardId, Integer customCostInStars) {
        Reward reward = rewardRepository.findRewardByRewardId(rewardId);
        ParentRewardCost parentRewardCost = parentRewardCostRepository.findByParentIdAndReward(parentId, reward)
                .orElse(new ParentRewardCost()
                        .setParent(new User().setId(parentId))
                        .setReward(new Reward().setRewardId(rewardId)));

        parentRewardCost.setCustomCostInStars(customCostInStars);
        parentRewardCostRepository.save(parentRewardCost);
    }

    @Override
    public void deleteUserReward(Long userRewardId) {
        UserReward userReward = userRewardRepository.findById(userRewardId)
                .orElseThrow(() -> new IllegalArgumentException("User reward not found"));
        userRewardRepository.delete(userReward);
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

        Integer costInStars = parentRewardCostRepository.findByParentIdAndReward(user.getParent().getId(), reward)
                .map(ParentRewardCost::getCustomCostInStars)
                .orElse(reward.getDefaultCostInStars());

        if (userStars.getTotalStars() < costInStars) {
            throw new IllegalStateException("Not enough stars to exchange for this reward");
        }

        userStars.setTotalStars(userStars.getTotalStars() - costInStars);
        userStars.setStarsSpent(userStars.getStarsSpent() + costInStars);
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
        Integer costInStars = parentRewardCostRepository.findByParentIdAndReward(parentId, userReward.getReward())
                .map(ParentRewardCost::getCustomCostInStars)
                .orElse(userReward.getReward().getDefaultCostInStars());


        UserStars userStars = userStarsRepository.findByUserId(userReward.getUser().getId());
        userStars.setTotalStars(userStars.getTotalStars() + costInStars);
        userStarsRepository.save(userStars);

        userRewardRepository.delete(userReward);
    }

    @Override
    public List<ChildRewardsDTO> getChildrenRewards(Long parentId) {
        List<User> children = parentService.getChildrenOfParent(parentId);

        return children.stream().map(child -> {
            List<UserReward> rewards = userRewardRepository.findByUser(child);
            return childRewardsMapper.map(child, rewards);
        }).collect(Collectors.toList());
    }

}
