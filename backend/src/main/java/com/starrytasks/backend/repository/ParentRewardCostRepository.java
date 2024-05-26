package com.starrytasks.backend.repository;

import com.starrytasks.backend.api.internal.ParentRewardCost;
import com.starrytasks.backend.api.internal.Reward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParentRewardCostRepository extends JpaRepository<ParentRewardCost, Long> {
    Optional<ParentRewardCost> findByParentIdAndReward(Long parent_id, Reward reward);
    List<ParentRewardCost> findByParentId(Long parentId);
}
