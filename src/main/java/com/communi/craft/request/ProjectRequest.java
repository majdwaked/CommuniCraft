package com.communi.craft.request;

import com.communi.craft.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectRequest
{
    private String projectName;
    private String description;
    private DifficultyLevel difficultyLevel;
    private Integer groupSize;
    private Long categoryID;
    private Long profileID;
    private Long []skills;
    private Long []interests;
}
