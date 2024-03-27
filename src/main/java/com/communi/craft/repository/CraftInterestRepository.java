package com.communi.craft.repository;

import com.communi.craft.entity.CraftInterest;
import com.communi.craft.entity.CraftSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CraftInterestRepository extends JpaRepository<CraftInterest, Long>
{
    Optional<CraftInterest> findByInterestName(String interestName);

    @Query("SELECT i FROM CraftInterest i WHERE i.interestId IN :ids")
    List<CraftInterest> findByIds(List<Long> ids);
}
