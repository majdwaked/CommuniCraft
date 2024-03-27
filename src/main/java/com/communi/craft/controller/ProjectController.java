package com.communi.craft.controller;

import com.communi.craft.entity.Project;
import com.communi.craft.request.ProjectRequest;
import com.communi.craft.response.Response;
import com.communi.craft.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProjectController
{
    private final ProjectService projectService;

    @GetMapping("/projects")
    public ResponseEntity<Response<List<Project>>> getProjects(@RequestHeader("Authorization") String token)
    {
        return ResponseEntity.ok(projectService.getProjects(token.substring(7)));
    }

    @PostMapping("/project")
    public ResponseEntity<Response<Project>> createProject(@RequestHeader("Authorization") String token, @RequestBody ProjectRequest request)
    {
        return ResponseEntity.ok(projectService.createProject(token.substring(7), request));
    }

    @PostMapping("/project/{id}")
    public ResponseEntity<Response<Project>> updateProject(@RequestHeader("Authorization") String token, @PathVariable Long id, @RequestBody ProjectRequest request)
    {
        return ResponseEntity.ok(projectService.updateProject(token.substring(7), id, request));
    }

    @DeleteMapping("/project/{id}")
    public ResponseEntity<Response<Void>> deleteProject(@RequestHeader("Authorization") String token, @PathVariable Long id)
    {
        return ResponseEntity.ok(projectService.deleteProject(token.substring(7), id));
    }
}
