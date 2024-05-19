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

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer defaultCostInStars;

    @Column(nullable = false)
    private Boolean isDefault;

    @Column(nullable = true)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private User user;
}