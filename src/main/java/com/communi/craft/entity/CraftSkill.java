package com.communi.craft.entity;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "craftskills")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CraftSkill
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long skillId;

    @Column(nullable = false, length = 50)
    private String skillName;
}
