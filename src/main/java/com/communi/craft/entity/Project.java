package com.communi.craft.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "projects")
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

    @Column(nullable = false)
    private Integer groupSize;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    @JsonIgnore
    private User creator;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private ProjectCategory category;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    @JsonIgnore
    private UserProfile userProfile;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<ProjectSkill> projectSkills;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<ProjectInterest> projectInterests;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<ProjectCollaborator> projectCollaborators;

    @JsonProperty("creator_id")
    public Long getCreatorId()
    {
        return creator != null ? creator.getUserId() : null;
    }
}
