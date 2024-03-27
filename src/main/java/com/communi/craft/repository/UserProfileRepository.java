package com.communi.craft.repository;

import com.communi.craft.entity.User;
import com.communi.craft.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long>
{
    List<UserProfile> findByUser(User user);

    Optional<UserProfile> findByProfileName(String profileName);
}
