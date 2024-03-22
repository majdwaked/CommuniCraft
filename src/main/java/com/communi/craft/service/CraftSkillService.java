package com.communi.craft.service;

import com.communi.craft.entity.CraftSkill;
import com.communi.craft.error.DuplicationException;
import com.communi.craft.error.NotFoundException;
import com.communi.craft.repository.CraftSkillRepository;
import com.communi.craft.request.CraftSkillRequest;
import com.communi.craft.response.Response;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CraftSkillService
{
    private final CraftSkillRepository craftSkillRepository;

    public Response<List<CraftSkill>> getCraftSkills()
    {
        var skills = craftSkillRepository.findAll();
        return Response.<List<CraftSkill>>builder().status(200).message("success").data(skills).build();
    }

    public Response<CraftSkill> createCraftSkill(CraftSkillRequest request)
    {
        if(craftSkillRepository.findBySkillName(request.getSkillName()).isPresent())
        {
            throw new DuplicationException("duplicated skill");
        }

        var craftSkill = CraftSkill.builder().skillName(request.getSkillName()).build();
        craftSkillRepository.save(craftSkill);

        return Response.<CraftSkill>builder().status(201).message("skill created successfully").data(craftSkill).build();
    }

    @Transactional
    public Response<CraftSkill> updateCraftSkill(Long id, CraftSkillRequest request)
    {
        var skill = craftSkillRepository.findById(id);

        if(skill.isEmpty())
        {
            throw new NotFoundException("not found");
        }

        if(craftSkillRepository.findBySkillName(request.getSkillName()).isPresent() && !skill.get().getSkillName().equalsIgnoreCase(request.getSkillName()))
        {
            throw new DuplicationException("duplicated skill");
        }

        skill.get().setSkillName(request.getSkillName());
        craftSkillRepository.save(skill.get());

        return Response.<CraftSkill>builder().status(200).message("skill updated successfully").data(skill.get()).build();
    }
}
