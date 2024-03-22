package com.communi.craft.repository;

import com.communi.craft.entity.Material;
import com.communi.craft.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MaterialRepository extends JpaRepository<Material, Long>
{
    List<Material> findByUser(User user);
}
