package com.example.mytodobackend.user.dto;

public record ChangePasswordRequest (
        String oldPassword,
        String newPassword
) {}
