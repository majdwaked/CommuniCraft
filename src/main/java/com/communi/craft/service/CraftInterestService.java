package com.communi.craft.service;

import com.communi.craft.entity.CraftInterest;
import com.communi.craft.error.DuplicationException;
import com.communi.craft.error.NotFoundException;
import com.communi.craft.repository.CraftInterestRepository;
import com.communi.craft.request.CraftInterestRequest;
import com.communi.craft.response.Response;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CraftInterestService
{
    private final CraftInterestRepository craftInterestRepository;

    public Response<List<CraftInterest>> getCraftInterest()
    {
        var interests = craftInterestRepository.findAll();
        return Response.<List<CraftInterest>>builder().status(200).message("success").data(interests).build();
    }

    public Response<CraftInterest> createCraftInterest(CraftInterestRequest request)
    {
        if(craftInterestRepository.findByInterestName(request.getInterestName()).isPresent())
        {
            throw new DuplicationException("duplicated interest");
        }

        var craftInterest = CraftInterest.builder().interestName(request.getInterestName()).build();
        craftInterestRepository.save(craftInterest);

        return Response.<CraftInterest>builder().status(201).message("interest created successfully").data(craftInterest).build();
    }

    @Transactional
    public Response<CraftInterest> updateCraftInterest(Long id, CraftInterestRequest request)
    {
        var interest = craftInterestRepository.findById(id);

        if(interest.isEmpty())
        {
            throw new NotFoundException("not found");
        }

        if(craftInterestRepository.findByInterestName(request.getInterestName()).isPresent() && !interest.get().getInterestName().equalsIgnoreCase(request.getInterestName()))
        {
            throw new DuplicationException("duplicated interest");
        }

        interest.get().setInterestName(request.getInterestName());
        craftInterestRepository.save(interest.get());

        return Response.<CraftInterest>builder().status(200).message("interest updated successfully").data(interest.get()).build();
    }
}
