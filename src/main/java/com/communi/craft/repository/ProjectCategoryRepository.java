package com.communi.craft.repository;

import com.communi.craft.entity.ProjectCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectCategoryRepository extends JpaRepository<ProjectCategory, Long>
{
    Optional<ProjectCategory> findByCategoryName(String categoryName);
}