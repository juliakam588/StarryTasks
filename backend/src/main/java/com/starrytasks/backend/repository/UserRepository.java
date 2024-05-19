package com.starrytasks.backend.repository;

import com.starrytasks.backend.api.internal.User;
import com.starrytasks.backend.api.internal.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByParentId(Long parentId);

    Optional<User> findUserByEmail(String email);

    boolean existsByEmail(String email);

    User findUserById(Long id);

    Boolean existsByIdAndParentId(Long childId, Long parentId);

}
