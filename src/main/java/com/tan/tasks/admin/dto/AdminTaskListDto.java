package com.tan.tasks.admin.dto;

import java.util.UUID;

public record AdminTaskListDto(
        UUID id,
        String title,
        String description,
        Double progress
) {
}
