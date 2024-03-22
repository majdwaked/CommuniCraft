package com.communi.craft.controller;

import com.communi.craft.entity.ProjectCategory;
import com.communi.craft.request.ProjectCategoryRequest;
import com.communi.craft.response.Response;
import com.communi.craft.service.ProjectCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProjectCategoryController
{
    private final ProjectCategoryService projectCategoryService;

    @GetMapping("/project-categories")
    public ResponseEntity<Response<List<ProjectCategory>>> getProjectCategories()
    {
        return ResponseEntity.ok(projectCategoryService.getProjectCategories());
    }

    @PostMapping("/admin/project-category")
    public ResponseEntity<Response<ProjectCategory>> createProjectCategory(@RequestBody ProjectCategoryRequest request)
    {
        return ResponseEntity.ok(projectCategoryService.createProjectCategory(request));
    }

    @PostMapping("/admin/project-category/{id}")
    public ResponseEntity<Response<ProjectCategory>> updateProjectCategory(@PathVariable Long id, @RequestBody ProjectCategoryRequest request)
    {
        return ResponseEntity.ok(projectCategoryService.updateProjectCategory(id, request));
    }
}