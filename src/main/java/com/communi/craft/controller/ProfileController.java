package com.communi.craft.controller;

import com.communi.craft.entity.UserProfile;
import com.communi.craft.request.ProfileRequest;
import com.communi.craft.response.Response;
import com.communi.craft.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProfileController
{
    private final ProfileService profileService;

    @GetMapping("/profiles")
    public ResponseEntity<Response<List<UserProfile>>> getProfiles(@RequestHeader("Authorization") String token)
    {
        return ResponseEntity.ok(profileService.getUserProfiles(token.substring(7)));
    }

    @PostMapping("/profile")
    public ResponseEntity<Response<UserProfile>> createProfile(@RequestHeader("Authorization") String token, @RequestBody ProfileRequest request)
    {
        return ResponseEntity.ok(profileService.createProfile(token.substring(7), request));
    }

    @PostMapping("/profile/{id}")
    public ResponseEntity<Response<UserProfile>> updateProfile(@RequestHeader("Authorization") String token, @PathVariable Long id, @RequestBody ProfileRequest request)
    {
        return ResponseEntity.ok(profileService.updateProfile(token.substring(7), id, request));
    }

    @DeleteMapping("/profile/{id}")
    public ResponseEntity<Response<Void>> deleteProfile(@RequestHeader("Authorization") String token, @PathVariable Long id)
    {
        return ResponseEntity.ok(profileService.deleteProfile(token.substring(7), id));
    }
}
