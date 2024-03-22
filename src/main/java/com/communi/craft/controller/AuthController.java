package com.communi.craft.controller;

import com.communi.craft.request.UserLoginRequest;
import com.communi.craft.request.UserRegisterRequest;
import com.communi.craft.response.Response;
import com.communi.craft.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController
{
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Response<Void>> register(@RequestBody UserRegisterRequest request)
    {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<Response<String>> login(@RequestBody UserLoginRequest request)
    {
        return ResponseEntity.ok(authService.login(request));
    }
}
