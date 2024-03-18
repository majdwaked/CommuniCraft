package com.communi.craft.entity;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "ProjectSkills")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectSkill
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectSkillId;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "skill_id")
    private CraftSkill skill;
}
