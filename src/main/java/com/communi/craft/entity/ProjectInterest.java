package com.communi.craft.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "projectinterests")
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
    @JsonIgnore
    private Project project;

    @ManyToOne
    @JoinColumn(name = "interest_id")
    private CraftInterest interest;
}
