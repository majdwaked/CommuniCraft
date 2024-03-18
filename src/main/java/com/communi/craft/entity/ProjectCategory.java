package com.communi.craft.entity;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "ProjectCategories")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectCategory
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(nullable = false, length = 100)
    private String categoryName;
}