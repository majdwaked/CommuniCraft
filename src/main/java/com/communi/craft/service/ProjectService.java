package com.communi.craft.service;

import com.communi.craft.config.JwtService;
import com.communi.craft.entity.Project;
import com.communi.craft.entity.ProjectInterest;
import com.communi.craft.entity.ProjectSkill;
import com.communi.craft.error.NotFoundException;
import com.communi.craft.error.UnauthorizedException;
import com.communi.craft.repository.*;
import com.communi.craft.request.ProjectRequest;
import com.communi.craft.response.Response;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService
{
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final ProjectCategoryRepository projectCategoryRepository;
    private final CraftInterestRepository craftInterestRepository;
    private final CraftSkillRepository craftSkillRepository;
    private final ProjectRepository projectRepository;
    private final ProjectSkillRepository projectSkillRepository;
    private final ProjectInterestRepository projectInterestRepository;
    private final UserProfileRepository userProfileRepository;

    public Response<List<Project>> getProjects(String token)
    {
        String email = jwtService.extractUsername(token);
        var user = userRepository.findByEmail(email);

        if(user.isEmpty())
        {
            throw new NotFoundException("user not found");
        }

        List<Project> projects = projectRepository.findByCreator(user.get());
        return Response.<List<Project>>builder().status(200).message("success").data(projects).build();
    }

    public Response<Project> createProject(String token, ProjectRequest request)
    {
        String email = jwtService.extractUsername(token);
        var user = userRepository.findByEmail(email);

        if(user.isEmpty())
        {
            throw new NotFoundException("user not found");
        }

        if(projectCategoryRepository.findById(request.getCategoryID()).isEmpty())
        {
            throw new NotFoundException("category not found");
        }

        if(craftSkillRepository.findByIds(Arrays.asList(request.getSkills())).size() != request.getSkills().length)
        {
            throw new NotFoundException("one or more skill not found");
        }

        if(craftInterestRepository.findByIds(Arrays.asList(request.getInterests())).size() != request.getInterests().length)
        {
            throw new NotFoundException("one or more interest not found");
        }

        if(userProfileRepository.findById(request.getProfileID()).isEmpty())
        {
            throw new NotFoundException("profile not found");
        }

        var project = Project.builder()
                .projectName(request.getProjectName())
                .description(request.getDescription())
                .difficultyLevel(request.getDifficultyLevel())
                .groupSize(request.getGroupSize())
                .creator(user.get())
                .userProfile(userProfileRepository.findById(request.getProfileID()).get())
                .category(projectCategoryRepository.findById(request.getCategoryID()).get())
                .projectInterests(null)
                .projectSkills(null)
                .projectCollaborators(null)
                .build();

        var interests = Arrays.stream(request.getInterests()).map(id -> ProjectInterest.builder().project(project).interest(craftInterestRepository.findById(id).get()).build()).toList();
        var skills = Arrays.stream(request.getSkills()).map(id -> ProjectSkill.builder().project(project).skill(craftSkillRepository.findById(id).get()).build()).toList();

        project.setProjectInterests(interests);
        project.setProjectSkills(skills);

        projectRepository.save(project);

        return Response.<Project>builder().status(201).message("project created successfully").data(project).build();
    }

    @Transactional
    public Response<Project> updateProject(String token, Long projectID, ProjectRequest request)
    {
        String email = jwtService.extractUsername(token);
        var user = userRepository.findByEmail(email);

        if(user.isEmpty())
        {
            throw new NotFoundException("user not found");
        }

        var project = projectRepository.findById(projectID);

        if(project.isEmpty())
        {
            throw new NotFoundException("project not found");
        }

        if(!project.get().getCreator().getUserId().equals(user.get().getUserId()))
        {
            throw new UnauthorizedException("unauthorized access");
        }

        if(projectCategoryRepository.findById(request.getCategoryID()).isEmpty())
        {
            throw new NotFoundException("category not found");
        }

        if(craftSkillRepository.findByIds(Arrays.asList(request.getSkills())).size() != request.getSkills().length)
        {
            throw new NotFoundException("one or more skill not found");
        }

        if(craftInterestRepository.findByIds(Arrays.asList(request.getInterests())).size() != request.getInterests().length)
        {
            throw new NotFoundException("one or more interest not found");
        }

        if(userProfileRepository.findById(request.getProfileID()).isEmpty())
        {
            throw new NotFoundException("profile not found");
        }

        var interests = Arrays.stream(request.getInterests()).map(id -> ProjectInterest.builder().project(project.get()).interest(craftInterestRepository.findById(id).get()).build()).toList();
        var skills = Arrays.stream(request.getSkills()).map(id -> ProjectSkill.builder().project(project.get()).skill(craftSkillRepository.findById(id).get()).build()).toList();

        projectSkillRepository.deleteAllInBatch(project.get().getProjectSkills());
        projectInterestRepository.deleteAllInBatch(project.get().getProjectInterests());

        project.get().setProjectName(request.getProjectName());
        project.get().setDescription(request.getDescription());
        project.get().setDifficultyLevel(request.getDifficultyLevel());
        project.get().setProjectSkills(new ArrayList<>(skills));
        project.get().setProjectInterests(new ArrayList<>(interests));
        project.get().setGroupSize(request.getGroupSize());
        project.get().setUserProfile(userProfileRepository.findById(request.getProfileID()).get());
        project.get().setCategory(projectCategoryRepository.findById(request.getCategoryID()).get());

        projectRepository.save(project.get());

        return Response.<Project>builder().status(201).message("project updated successfully").data(project.get()).build();
    }

    @Transactional
    public Response<Void> deleteProject(String token, Long id)
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

        projectRepository.delete(project.get());
        return Response.<Void>builder().status(200).message("project deleted successfully").build();
    }
}
