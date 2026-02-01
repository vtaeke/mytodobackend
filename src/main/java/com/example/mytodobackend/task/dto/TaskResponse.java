package com.example.mytodobackend.task.dto;

import java.time.LocalDate;
import java.util.List;

public record TaskResponse(
        Long id,
        String title,
        String description,
        String status,
        String priority,
        LocalDate deadLine,
        List<String> tags
) {}