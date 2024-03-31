package com.communi.craft.controller;

import com.communi.craft.service.ExternalDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ExternalDataController
{
    private final ExternalDataService externalDataService;

    @GetMapping("/external/materials")
    public ResponseEntity<String> getExternalMaterials()
    {
        return ResponseEntity.ok().header("Content-Type", "application/json").body(externalDataService.getExternalMaterials());
    }

    @GetMapping("/external/tools")
    public ResponseEntity<String> getExternalTools()
    {
        return ResponseEntity.ok().header("Content-Type", "application/json").body(externalDataService.getExternalTools());
    }

    @GetMapping("/external/project-ideas")
    public ResponseEntity<String> getExternalProjectIdeas()
    {
        return ResponseEntity.ok().header("Content-Type", "application/json").body(externalDataService.getExternalProjectIdeas());
    }
}
