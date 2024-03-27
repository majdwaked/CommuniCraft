package com.communi.craft.repository;

import com.communi.craft.entity.CraftSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CraftSkillRepository extends JpaRepository<CraftSkill, Long>
{
    Optional<CraftSkill> findBySkillName(String skillName);

    @Query("SELECT s FROM CraftSkill s WHERE s.skillId IN :ids")
    List<CraftSkill> findByIds(List<Long> ids);
}
