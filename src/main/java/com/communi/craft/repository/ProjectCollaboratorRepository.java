package com.communi.craft.repository;

import com.communi.craft.entity.ProjectCollaborator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectCollaboratorRepository extends JpaRepository<ProjectCollaborator, Long>
{
}