package com.communi.craft.repository;

import com.communi.craft.entity.Project;
import com.communi.craft.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long>
{
    List<Project> findByCreator(User creator);
}
