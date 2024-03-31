package com.communi.craft.request;

import com.communi.craft.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectCollaboratorRequest
{
    private Long []userIDs;
}
