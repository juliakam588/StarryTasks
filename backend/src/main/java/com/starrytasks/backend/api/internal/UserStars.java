package com.starrytasks.backend.api.internal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Entity
@Table(name = "user_stars")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserStars {
    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private Integer totalStars = 0;

    @Column(nullable = false)
    private Integer starsSpent = 0;

    @Transient
    private Integer currentStars;

    @PostLoad
    public void calculateCurrentStars() {
        this.currentStars = totalStars - starsSpent;
    }
}