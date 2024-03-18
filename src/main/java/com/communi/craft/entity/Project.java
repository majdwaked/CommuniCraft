package com.communi.craft.entity;

import lombok.*;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Projects")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Project
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;

    @Column(nullable = false, length = 100)
    private String projectName;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DifficultyLevel difficultyLevel;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private ProjectCategory category;

    @OneToMany(mappedBy = "project")
    private List<ProjectSkill> projectSkills;

    @OneToMany(mappedBy = "project")
    private List<ProjectInterest> projectInterests;

    @OneToMany(mappedBy = "project")
    private List<ProjectCollaborator> projectCollaborators;
}
