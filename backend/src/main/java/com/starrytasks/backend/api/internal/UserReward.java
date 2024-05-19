package com.starrytasks.backend.api.internal;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "user_rewards")
public class UserReward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userRewardId;

    @ManyToOne
    @JoinColumn(name = "rewardId", nullable = false)
    private Reward reward;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDate redemptionDate;

    @Column(nullable = false)
    private boolean isGranted = false;

}