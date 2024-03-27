package com.communi.craft.repository;

import com.communi.craft.entity.Tool;
import com.communi.craft.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ToolRepository extends JpaRepository<Tool, Long>
{
    List<Tool> findByUser(User user);
}
