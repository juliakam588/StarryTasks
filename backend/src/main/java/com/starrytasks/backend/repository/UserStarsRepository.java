package com.starrytasks.backend.repository;


import com.starrytasks.backend.api.internal.UserStars;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStarsRepository extends JpaRepository<UserStars, Long> {
    UserStars findByUserId(Long id);
}
