package com.communi.craft.service;

import com.communi.craft.config.JwtService;
import com.communi.craft.entity.UserProfile;
import com.communi.craft.error.DuplicationException;
import com.communi.craft.error.NotFoundException;
import com.communi.craft.error.UnauthorizedException;
import com.communi.craft.repository.UserProfileRepository;
import com.communi.craft.repository.UserRepository;
import com.communi.craft.request.ProfileRequest;
import com.communi.craft.response.Response;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileService
{
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final UserProfileRepository userProfileRepository;

    public Response<List<UserProfile>> getUserProfiles(String token)
    {
        String email = jwtService.extractUsername(token);

        var user = userRepository.findByEmail(email);

        if(user.isEmpty())
        {
            throw new NotFoundException("user not found");
        }

        List<UserProfile> profiles = userProfileRepository.findByUser(user.get());
        return Response.<List<UserProfile>>builder().status(200).message("success").data(profiles).build();
    }

    public Response<UserProfile> createProfile(String token, ProfileRequest request)
    {
        String email = jwtService.extractUsername(token);

        var user = userRepository.findByEmail(email);

        if(user.isEmpty())
        {
            throw new NotFoundException("user not found");
        }

        if(userProfileRepository.findByProfileName(request.getProfileName()).isPresent())
        {
            throw new DuplicationException("duplicated profile name");
        }

        var profile = UserProfile.builder()
                .profileName(request.getProfileName())
                .user(user.get())
                .projects(null)
                .build();
        userProfileRepository.save(profile);

        return Response.<UserProfile>builder().status(201).message("profile created successfully").data(profile).build();
    }

    @Transactional
    public Response<UserProfile> updateProfile(String token, Long id, ProfileRequest request)
    {
        String email = jwtService.extractUsername(token);

        var user = userRepository.findByEmail(email);

        if(user.isEmpty())
        {
            throw new NotFoundException("user not found");
        }

        var profile = userProfileRepository.findById(id);

        if(profile.isEmpty())
        {
            throw new NotFoundException("profile not found");
        }

        if(!profile.get().getUserId().equals(user.get().getUserId()))
        {
            throw new UnauthorizedException("unauthorized access");
        }

        if(!profile.get().getProfileName().equalsIgnoreCase(request.getProfileName()) && userProfileRepository.findByProfileName(request.getProfileName()).isPresent())
        {
            throw new DuplicationException("duplicated profile name");
        }

        profile.get().setProfileName(request.getProfileName());
        userProfileRepository.save(profile.get());

        return Response.<UserProfile>builder().status(200).message("profile updated successfully").data(profile.get()).build();
    }

    @Transactional
    public Response<Void> deleteProfile(String token, Long id)
    {
        String email = jwtService.extractUsername(token);

        var user = userRepository.findByEmail(email);

        if(user.isEmpty())
        {
            throw new NotFoundException("user not found");
        }

        var profile = userProfileRepository.findById(id);

        if(profile.isEmpty())
        {
            throw new NotFoundException("profile not found");
        }

        if(!profile.get().getUserId().equals(user.get().getUserId()))
        {
            throw new UnauthorizedException("unauthorized access");
        }

        userProfileRepository.delete(profile.get());
        return Response.<Void>builder().status(200).message("Profile deleted successfully").build();
    }
}
