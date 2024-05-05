package com.starrytasks.backend.repository;


import com.starrytasks.backend.api.internal.User;
import com.starrytasks.backend.api.internal.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    UserProfile findByUserId(Long id);
}

