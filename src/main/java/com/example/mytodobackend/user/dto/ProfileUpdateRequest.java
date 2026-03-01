package com.example.mytodobackend.user.dto;

import java.time.LocalDate;

public record ProfileUpdateRequest (
        String firstName,
        String avatarUrl,
        LocalDate birthDate
) {}
