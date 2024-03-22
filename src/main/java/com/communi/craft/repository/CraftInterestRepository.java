package com.communi.craft.repository;

import com.communi.craft.entity.CraftInterest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CraftInterestRepository extends JpaRepository<CraftInterest, Long>
{
    Optional<CraftInterest> findByInterestName(String interestName);
}
