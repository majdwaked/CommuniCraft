package com.communi.craft.controller;

import com.communi.craft.entity.Project;
import com.communi.craft.entity.ProjectCollaborator;
import com.communi.craft.request.ProjectCollaboratorRequest;
import com.communi.craft.request.ProjectRequest;
import com.communi.craft.response.Response;
import com.communi.craft.service.ProjectCollaboratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProjectCollaboratorController
{
    private final ProjectCollaboratorService projectCollaboratorService;

    @GetMapping("/project/{id}/collaborators")
    public ResponseEntity<Response<List<ProjectCollaborator>>> getProjectCollaborators(@PathVariable Long id, @RequestHeader("Authorization") String token)
    {
        return ResponseEntity.ok(projectCollaboratorService.getProjectCollaborators(id, token.substring(7)));
    }

    @PostMapping("/project/{id}/collaborator")
    public ResponseEntity<Response<List<ProjectCollaborator>>> createProjectCollaborators(@PathVariable Long id, @RequestHeader("Authorization") String token, @RequestBody ProjectCollaboratorRequest request)
    {
        return ResponseEntity.ok(projectCollaboratorService.createProjectCollaborators(id, token.substring(7), request));
    }

    @DeleteMapping("/project/{pid}/collaborator/{cid}")
    public ResponseEntity<Response<Void>> deleteProjectCollaborator(@RequestHeader("Authorization") String token, @PathVariable Long pid, @PathVariable Long cid)
    {
        return ResponseEntity.ok(projectCollaboratorService.deleteProjectCollaborator(token.substring(7), pid, cid));
    }
}
