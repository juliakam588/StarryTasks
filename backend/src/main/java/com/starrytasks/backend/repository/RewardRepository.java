package com.starrytasks.backend.repository;

import com.starrytasks.backend.api.internal.Reward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RewardRepository extends JpaRepository<Reward, Long> {
    Reward findRewardByRewardId(long id);
}
