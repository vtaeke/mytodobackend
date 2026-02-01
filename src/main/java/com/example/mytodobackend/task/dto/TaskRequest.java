package com.example.mytodobackend.task.dto;

import java.time.LocalDate;
import java.util.List;

public record TaskRequest(
        String title,
        String description,
        String status,
        String priority,
        LocalDate deadLine,
        List<String> tags
) {}
