package com.starrytasks.backend.api.internal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Entity
@Table(name = "reward_templates")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class RewardTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rewardTemplateId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer defaultCostInStars;

}