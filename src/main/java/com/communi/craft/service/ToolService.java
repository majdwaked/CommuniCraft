package com.communi.craft.service;

import com.communi.craft.config.JwtService;
import com.communi.craft.entity.Tool;
import com.communi.craft.error.DuplicationException;
import com.communi.craft.error.NotFoundException;
import com.communi.craft.error.UnauthorizedException;
import com.communi.craft.repository.ToolRepository;
import com.communi.craft.repository.UserRepository;
import com.communi.craft.request.ToolRequest;
import com.communi.craft.response.Response;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ToolService
{
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final ToolRepository toolRepository;

    public Response<List<Tool>> getAllTools(String token)
    {
        var user = userRepository.findByEmail(jwtService.extractUsername(token));

        if(user.isEmpty())
        {
            throw new NotFoundException("user not found");
        }

        List<Tool> tools = toolRepository.findByUser(user.get());
        return Response.<List<Tool>>builder().status(200).message("Success").data(tools).build();
    }

    public Response<Tool> createTool(String token, ToolRequest request)
    {
        var user = userRepository.findByEmail(jwtService.extractUsername(token));

        if(user.isEmpty())
        {
            throw new NotFoundException("user not found");
        }

        if (toolRepository.findByUser(user.get()).stream().anyMatch(t -> t.getToolName().equalsIgnoreCase(request.getToolName())))
        {
            throw new DuplicationException("Duplicate tool name");
        }

        var tool = Tool.builder()
                .toolName(request.getToolName())
                .quantityAvailable(request.getQuantityAvailable())
                .user(user.get())
                .build();
        toolRepository.save(tool);

        return Response.<Tool>builder().status(201).message("Tool created successfully").data(tool).build();
    }

    @Transactional
    public Response<Tool> updateTool(String token, Long id, ToolRequest request)
    {
        var user = userRepository.findByEmail(jwtService.extractUsername(token));

        if(user.isEmpty())
        {
            throw new NotFoundException("user not found");
        }

        var tool = toolRepository.findById(id);

        if(tool.isEmpty())
        {
            throw new NotFoundException("material not found");
        }

        if(!tool.get().getUser().getUserId().equals(user.get().getUserId()))
        {
            throw new UnauthorizedException("unauthorized operation");
        }

        tool.get().setToolName(request.getToolName());
        tool.get().setQuantityAvailable(request.getQuantityAvailable());
        toolRepository.save(tool.get());

        return Response.<Tool>builder().status(200).message("Tool updated successfully").data(tool.get()).build();
    }

    @Transactional
    public Response<Void> deleteTool(String token, Long id)
    {
        var user = userRepository.findByEmail(jwtService.extractUsername(token));

        if(user.isEmpty())
        {
            throw new NotFoundException("user not found");
        }

        var tool = toolRepository.findById(id);

        if(tool.isEmpty())
        {
            throw new NotFoundException("tool not found");
        }

        if(!tool.get().getUser().getUserId().equals(user.get().getUserId()))
        {
            throw new UnauthorizedException("unauthorized operation");
        }

        toolRepository.deleteById(id);
        return Response.<Void>builder().status(200).message("Tool deleted successfully").build();
    }
}
