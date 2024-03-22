package com.communi.craft.controller;

import java.util.List;
import com.communi.craft.entity.CraftInterest;
import com.communi.craft.request.CraftInterestRequest;
import com.communi.craft.response.Response;
import com.communi.craft.service.CraftInterestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CraftInterestController
{
    private final CraftInterestService craftInterestService;

    @GetMapping("/craft-interests")
    public ResponseEntity<Response<List<CraftInterest>>> getCraftInterest()
    {
        return ResponseEntity.ok(craftInterestService.getCraftInterest());
    }

    @PostMapping("/admin/craft-interest")
    public ResponseEntity<Response<CraftInterest>> createCraftInterest(@RequestBody CraftInterestRequest request)
    {
        return ResponseEntity.ok(craftInterestService.createCraftInterest(request));
    }

    @PostMapping("/admin/craft-interest/{id}")
    public ResponseEntity<Response<CraftInterest>> updateCraftInterest(@PathVariable Long id, @RequestBody CraftInterestRequest request)
    {
        return ResponseEntity.ok(craftInterestService.updateCraftInterest(id, request));
    }
}
