package com.starrytasks.backend.repository;

import com.starrytasks.backend.api.internal.User;
import com.starrytasks.backend.api.internal.UserReward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRewardRepository extends JpaRepository<UserReward, Long> {

    List<UserReward> findByUser(User child);

}
