package com.sagar.repository;

import com.sagar.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByCategoryNameIgnoreCase(String categoryName);
}
