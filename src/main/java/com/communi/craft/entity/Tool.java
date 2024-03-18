package com.communi.craft.entity;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "Tools")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tool {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long toolId;

    @Column(nullable = false, length = 100)
    private String toolName;

    @Column(nullable = false)
    private int quantityAvailable;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}