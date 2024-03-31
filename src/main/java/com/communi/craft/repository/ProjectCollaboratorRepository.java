package com.communi.craft.repository;

import com.communi.craft.entity.Project;
import com.communi.craft.entity.ProjectCollaborator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectCollaboratorRepository extends JpaRepository<ProjectCollaborator, Long>
{
    List<ProjectCollaborator> findByProject(Project project);
}