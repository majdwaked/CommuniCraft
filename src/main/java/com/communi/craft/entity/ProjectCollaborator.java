package com.communi.craft.entity;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "ProjectCollaborators")
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
    private Project project;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;
}
