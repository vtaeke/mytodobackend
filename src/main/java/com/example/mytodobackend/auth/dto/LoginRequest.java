package com.example.mytodobackend.auth.dto;

public record LoginRequest(
    String email,
    String password
){}
