package com.communi.craft.controller;

import com.communi.craft.entity.CraftSkill;
import com.communi.craft.request.CraftSkillRequest;
import com.communi.craft.response.Response;
import com.communi.craft.service.CraftSkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CraftSkillController
{
    private final CraftSkillService craftSkillService;

    @GetMapping("/craft-skills")
    public ResponseEntity<Response<List<CraftSkill>>> getCraftSkills()
    {
        return ResponseEntity.ok(craftSkillService.getCraftSkills());
    }

    @PostMapping("/admin/craft-skill")
    public ResponseEntity<Response<CraftSkill>> createCraftSkill(@RequestBody CraftSkillRequest request)
    {
        return ResponseEntity.ok(craftSkillService.createCraftSkill(request));
    }

    @PostMapping("/admin/craft-skill/{id}")
    public ResponseEntity<Response<CraftSkill>> updateCraftSkill(@PathVariable Long id, @RequestBody CraftSkillRequest request)
    {
        return ResponseEntity.ok(craftSkillService.updateCraftSkill(id, request));
    }
}
