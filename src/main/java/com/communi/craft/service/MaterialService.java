package com.communi.craft.service;

import com.communi.craft.config.JwtService;
import com.communi.craft.entity.Material;
import com.communi.craft.error.DuplicationException;
import com.communi.craft.error.NotFoundException;
import com.communi.craft.error.UnauthorizedException;
import com.communi.craft.repository.MaterialRepository;
import com.communi.craft.repository.UserRepository;
import com.communi.craft.request.MaterialRequest;
import com.communi.craft.response.Response;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialService
{
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final MaterialRepository materialRepository;

    public Response<List<Material>> getAllMaterials(String token)
    {
        var user = userRepository.findByEmail(jwtService.extractUsername(token));

        if(user.isEmpty())
        {
            throw new NotFoundException("user not found");
        }

        List<Material> materials = materialRepository.findByUser(user.get());
        return Response.<List<Material>>builder().status(200).message("Success").data(materials).build();
    }

    public Response<Material> createMaterial(String token, MaterialRequest request)
    {
        var user = userRepository.findByEmail(jwtService.extractUsername(token));

        if(user.isEmpty())
        {
            throw new NotFoundException("user not found");
        }

        if (materialRepository.findByUser(user.get()).stream().anyMatch(m -> m.getMaterialName().equalsIgnoreCase(request.getMaterialName())))
        {
            throw new DuplicationException("Duplicate material name");
        }

        var material = Material.builder()
                .materialName(request.getMaterialName())
                .quantityAvailable(request.getQuantityAvailable())
                .user(user.get())
                .build();
        materialRepository.save(material);

        return Response.<Material>builder().status(201).message("Material created successfully").data(material).build();
    }

    @Transactional
    public Response<Material> updateMaterial(String token, Long id, MaterialRequest request)
    {
        var user = userRepository.findByEmail(jwtService.extractUsername(token));

        if(user.isEmpty())
        {
            throw new NotFoundException("user not found");
        }

        var material = materialRepository.findById(id);

        if(material.isEmpty())
        {
            throw new NotFoundException("material not found");
        }

        if(!material.get().getUser().getUserId().equals(user.get().getUserId()))
        {
            throw new UnauthorizedException("unauthorized operation");
        }

        material.get().setMaterialName(request.getMaterialName());
        material.get().setQuantityAvailable(request.getQuantityAvailable());
        materialRepository.save(material.get());

        return Response.<Material>builder().status(200).message("Material updated successfully").data(material.get()).build();
    }

    @Transactional
    public Response<Void> deleteMaterial(String token, Long id)
    {
        var user = userRepository.findByEmail(jwtService.extractUsername(token));

        if(user.isEmpty())
        {
            throw new NotFoundException("user not found");
        }

        var material = materialRepository.findById(id);

        if(material.isEmpty())
        {
            throw new NotFoundException("material not found");
        }

        if(!material.get().getUser().getUserId().equals(user.get().getUserId()))
        {
            throw new UnauthorizedException("unauthorized operation");
        }

        materialRepository.deleteById(id);
        return Response.<Void>builder().status(200).message("Material deleted successfully").build();
    }
}