package com.communi.craft.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "projectskills")
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
    @JsonIgnore
    private Project project;

    @ManyToOne
    @JoinColumn(name = "skill_id")
    private CraftSkill skill;
}
