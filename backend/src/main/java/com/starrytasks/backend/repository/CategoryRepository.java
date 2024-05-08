package com.starrytasks.backend.repository;

import com.starrytasks.backend.api.internal.Category;
import com.starrytasks.backend.api.internal.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
}
