package com.communi.craft.service;

import com.communi.craft.config.JwtService;
import com.communi.craft.entity.ProjectCollaborator;
import com.communi.craft.entity.Role;
import com.communi.craft.error.DuplicationException;
import com.communi.craft.error.NotFoundException;
import com.communi.craft.error.UnauthorizedException;
import com.communi.craft.repository.ProjectCollaboratorRepository;
import com.communi.craft.repository.ProjectRepository;
import com.communi.craft.repository.UserRepository;
import com.communi.craft.request.ProjectCollaboratorRequest;
import com.communi.craft.response.Response;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProjectCollaboratorService
{
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ProjectCollaboratorRepository projectCollaboratorRepository;

    public Response<List<ProjectCollaborator>> getProjectCollaborators(Long id, String token)
    {
        String email = jwtService.extractUsername(token);
        var user = userRepository.findByEmail(email);

        if(user.isEmpty())
        {
            throw new NotFoundException("user not found");
        }

        var project = projectRepository.findById(id);

        if(project.isEmpty())
        {
            throw new NotFoundException("project not found");
        }

        if(!project.get().getCreatorId().equals(user.get().getUserId()) && project.get().getProjectCollaborators().stream().noneMatch(pc -> pc.getUserId().equals(user.get().getUserId())))
        {
            throw new UnauthorizedException("unauthorized access");
        }

        List<ProjectCollaborator> projectCollaborators = projectCollaboratorRepository.findByProject(project.get());
        return Response.<List<ProjectCollaborator>>builder().status(200).message("success").data(projectCollaborators).build();
    }

    @Transactional
    public Response<List<ProjectCollaborator>> createProjectCollaborators(Long id, String token, ProjectCollaboratorRequest request)
    {
        String email = jwtService.extractUsername(token);
        var user = userRepository.findByEmail(email);

        if(user.isEmpty())
        {
            throw new NotFoundException("user not found");
        }

        var project = projectRepository.findById(id);

        if(project.isEmpty())
        {
            throw new NotFoundException("project not found");
        }

        if(!project.get().getCreatorId().equals(user.get().getUserId()))
        {
            throw new UnauthorizedException("unauthorized access");
        }

        if((long) userRepository.findAllById(List.of(request.getUserIDs())).size() != request.getUserIDs().length)
        {
            throw new NotFoundException("one or more collaborator not found");
        }

        project.get().getProjectCollaborators().forEach((c) ->{
            if(Arrays.stream(request.getUserIDs()).anyMatch(uid -> Objects.equals(uid, c.getUserId())))
            {
                throw new DuplicationException("duplicated collaborator");
            }
        });

        List<ProjectCollaborator> collaborators = Arrays.stream(request.getUserIDs()).map(uid -> ProjectCollaborator.builder().role(Role.collaborator).project(project.get()).user(userRepository.findById(uid).get()).build()).toList();
        projectCollaboratorRepository.saveAll(collaborators);

        return Response.<List<ProjectCollaborator>>builder().status(200).message("success").data(collaborators).build();
    }

    @Transactional
    public Response<Void> deleteProjectCollaborator(String token, Long pid, Long cid)
    {
        String email = jwtService.extractUsername(token);
        var user = userRepository.findByEmail(email);

        if(user.isEmpty())
        {
            throw new NotFoundException("user not found");
        }

        var project = projectRepository.findById(pid);

        if(project.isEmpty())
        {
            throw new NotFoundException("project not found");
        }

        if(!project.get().getCreatorId().equals(user.get().getUserId()))
        {
            throw new UnauthorizedException("unauthorized access");
        }

        var collaborator = projectCollaboratorRepository.findById(cid);

        if(collaborator.isEmpty())
        {
            throw new NotFoundException("collaborator not found");
        }

        project.get().getProjectCollaborators().remove(collaborator.get());
        projectCollaboratorRepository.delete(collaborator.get());

        return Response.<Void>builder().status(200).message("collaborator deleted successfully").build();
    }
}
