package com.communi.craft.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "craftinterests")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CraftInterest
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long interestId;

    @Column(nullable = false, length = 50)
    private String interestName;
}