package com.communi.craft.controller;

import com.communi.craft.entity.Tool;
import com.communi.craft.request.ToolRequest;
import com.communi.craft.response.Response;
import com.communi.craft.service.ToolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ToolController
{
    private final ToolService toolService;

    @GetMapping("/tools")
    public ResponseEntity<Response<List<Tool>>> getAllTools(@RequestHeader("Authorization") String token)
    {
        return ResponseEntity.ok(toolService.getAllTools(token.substring(7)));
    }

    @PostMapping("/tool")
    public ResponseEntity<Response<Tool>> createTool(@RequestHeader("Authorization") String token, @RequestBody ToolRequest request)
    {
        return ResponseEntity.ok(toolService.createTool(token.substring(7), request));
    }

    @PostMapping("/tool/{id}")
    public ResponseEntity<Response<Tool>> updateTool(@RequestHeader("Authorization") String token, @PathVariable Long id, @RequestBody ToolRequest request)
    {
        return ResponseEntity.ok(toolService.updateTool(token.substring(7), id, request));
    }

    @DeleteMapping("/tool/{id}")
    public ResponseEntity<Response<Void>> deleteTool(@RequestHeader("Authorization") String token, @PathVariable Long id)
    {
        return ResponseEntity.ok(toolService.deleteTool(token.substring(7), id));
    }
}
