package com.communi.craft.repository;

import com.communi.craft.entity.CraftSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CraftSkillRepository extends JpaRepository<CraftSkill, Long>
{
    Optional<CraftSkill> findBySkillName(String skillName);
}
