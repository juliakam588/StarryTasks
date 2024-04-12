package com.starrytasks.backend.api.internal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Entity
@Table(name = "rewards")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Reward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reward_id")
    private Long rewardId;

    @ManyToOne(optional = true)
    @JoinColumn(name = "reward_template_id")
    private RewardTemplate rewardTemplate;

    @Column(nullable = true)
    private String customName;

    @Column(nullable = false)
    private Integer costInStars;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private User user;
}