package com.communi.craft.service;

import com.communi.craft.entity.ProjectCategory;
import com.communi.craft.error.DuplicationException;
import com.communi.craft.error.NotFoundException;
import com.communi.craft.repository.ProjectCategoryRepository;
import com.communi.craft.request.ProjectCategoryRequest;
import com.communi.craft.response.Response;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectCategoryService
{
    private final ProjectCategoryRepository projectCategoryRepository;

    public Response<List<ProjectCategory>> getProjectCategories()
    {
        List<ProjectCategory> categories = projectCategoryRepository.findAll();
        return Response.<List<ProjectCategory>>builder().status(200).message("success").data(categories).build();
    }

    public Response<ProjectCategory> createProjectCategory(ProjectCategoryRequest request)
    {
        if (projectCategoryRepository.findByCategoryName(request.getCategoryName()).isPresent())
        {
            throw new DuplicationException("Duplicated category");
        }

        ProjectCategory projectCategory = ProjectCategory.builder().categoryName(request.getCategoryName()).build();
        projectCategoryRepository.save(projectCategory);

        return Response.<ProjectCategory>builder().status(201).message("Category created successfully").data(projectCategory).build();
    }

    @Transactional
    public Response<ProjectCategory> updateProjectCategory(Long id, ProjectCategoryRequest request)
    {
        var category = projectCategoryRepository.findById(id);

        if (category.isEmpty())
        {
            throw new NotFoundException("Category not found");
        }

        if (projectCategoryRepository.findByCategoryName(request.getCategoryName()).isPresent() && !category.get().getCategoryName().equalsIgnoreCase(request.getCategoryName()))
        {
            throw new DuplicationException("Duplicated category");
        }

        category.get().setCategoryName(request.getCategoryName());
        projectCategoryRepository.save(category.get());

        return Response.<ProjectCategory>builder().status(200).message("Category updated successfully").data(category.get()).build();
    }
}