package com.communi.craft.controller;

import com.communi.craft.entity.Material;
import com.communi.craft.request.MaterialRequest;
import com.communi.craft.response.Response;
import com.communi.craft.service.MaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MaterialController
{
    private final MaterialService materialService;

    @GetMapping("/materials")
    public ResponseEntity<Response<List<Material>>> getAllMaterials(@RequestHeader("Authorization") String token)
    {
        return ResponseEntity.ok(materialService.getAllMaterials(token.substring(7)));
    }

    @PostMapping("/material")
    public ResponseEntity<Response<Material>> createMaterial(@RequestHeader("Authorization") String token, @RequestBody MaterialRequest request)
    {
        return ResponseEntity.ok(materialService.createMaterial(token.substring(7), request));
    }

    @PostMapping("/material/{id}")
    public ResponseEntity<Response<Material>> updateMaterial(@RequestHeader("Authorization") String token, @PathVariable Long id, @RequestBody MaterialRequest request)
    {
        return ResponseEntity.ok(materialService.updateMaterial(token.substring(7), id, request));
    }

    @DeleteMapping("/material/{id}")
    public ResponseEntity<Response<Void>> deleteMaterial(@RequestHeader("Authorization") String token, @PathVariable Long id)
    {
        return ResponseEntity.ok(materialService.deleteMaterial(token.substring(7), id));
    }
}