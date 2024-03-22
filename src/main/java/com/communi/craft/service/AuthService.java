package com.communi.craft.service;

import com.communi.craft.config.JwtService;
import com.communi.craft.entity.User;
import com.communi.craft.entity.UserType;
import com.communi.craft.error.DuplicationException;
import com.communi.craft.error.NotFoundException;
import com.communi.craft.repository.UserRepository;
import com.communi.craft.request.UserLoginRequest;
import com.communi.craft.request.UserRegisterRequest;
import com.communi.craft.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService
{
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    @Value(value = "${token.expiration.time}")
    private long userTokenLifeTime;

    public Response<Void> register(UserRegisterRequest request)
    {
        if(userRepository.findByEmail(request.getEmail()).isPresent())
        {
            throw new DuplicationException("duplicated user");
        }

        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .userType(UserType.user)
                .bio(request.getBio())
                .enabled(true)
                .build();
        userRepository.save(user);

        return Response.<Void>builder().status(201).message("user registered").build();
    }

    public Response<String> login(UserLoginRequest request)
    {
        if(userRepository.findByEmail(request.getEmail()).isEmpty())
        {
            throw new NotFoundException("not found");
        }

        var user = userRepository.findByEmail(request.getEmail()).get();

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword()))
        {
            throw new NotFoundException("not found");
        }


        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var token = jwtService.generateToken(user, userTokenLifeTime);

        return Response.<String>builder().status(200).message("logged in successfully").data(token).build();
    }
}
