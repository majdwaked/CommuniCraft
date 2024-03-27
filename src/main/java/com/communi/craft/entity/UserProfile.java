package com.communi.craft.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "userprofiles")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profileId;

    @Column(name = "profile_name")
    private String profileName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "userProfile", cascade = CascadeType.ALL)
    private List<Project> projects;

    @JsonProperty("user_id")
    public Long getUserId()
    {
        return user != null ? user.getUserId() : null;
    }
}
