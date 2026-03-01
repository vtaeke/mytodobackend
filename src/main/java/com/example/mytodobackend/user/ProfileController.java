package com.example.mytodobackend.user;

import com.example.mytodobackend.user.dto.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@CrossOrigin(origins = "http://localhost:5173")
public class ProfileController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ProfileController(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public ProfileResponse getProfile(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        return new ProfileResponse(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getAvatarUrl(),
                user.getBirthDate()
        );
    }

    @PutMapping
    public ProfileResponse updateProfile(
            @RequestBody
            ProfileUpdateRequest request,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();

        if (request.firstName() != null) {
            user.setFirstName(request.firstName());
        }
        if (request.avatarUrl() != null) {
            user.setAvatarUrl(request.avatarUrl());

        }
        if (request.birthDate() != null) {
            user.setBirthDate(request.birthDate());

        }

        userRepository.save(user);

        return new ProfileResponse(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getAvatarUrl(),
                user.getBirthDate()
        );
    }

    @PutMapping("/password")
    public void changePassword(
            @RequestBody ChangePasswordRequest request,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();

        if (!passwordEncoder.matches(
                request.oldPassword(),
                user.getPassword()
        )) {
            throw new RuntimeException("Invalid password");
        }

        user.setPassword(passwordEncoder.encode(request.newPassword()));

        userRepository.save(user);
    }

}
