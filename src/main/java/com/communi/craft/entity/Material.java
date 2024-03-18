package com.communi.craft.entity;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "Materials")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Material
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long materialId;

    @Column(nullable = false, length = 100)
    private String materialName;

    @Column(nullable = false)
    private int quantityAvailable;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
