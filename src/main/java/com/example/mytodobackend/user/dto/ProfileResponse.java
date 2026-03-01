package com.example.mytodobackend.user.dto;

import java.time.LocalDate;

public record ProfileResponse(
        Long id,
        String email,
        String firstName,
        String avatarUrl,
        LocalDate birthDate
) {}
