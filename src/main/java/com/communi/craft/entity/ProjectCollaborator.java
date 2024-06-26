package com.communi.craft.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "projectcollaborators")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectCollaborator
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectCollaboratorId;

    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonIgnore
    private Project project;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @JsonProperty("user_id")
    public Long getUserId()
    {
        return user != null ? user.getUserId() : null;
    }
}
