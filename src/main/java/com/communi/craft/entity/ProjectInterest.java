package com.communi.craft.entity;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "ProjectInterests")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectInterest
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectInterestId;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "interest_id")
    private CraftInterest interest;
}
