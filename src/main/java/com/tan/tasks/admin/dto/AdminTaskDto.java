package com.tan.tasks.admin.dto;

import com.tan.tasks.domain.entities.TaskPriority;
import com.tan.tasks.domain.entities.TaskStatus;

import java.util.UUID;

public record AdminTaskDto(
        UUID id,
        String title,
        TaskStatus status,
        TaskPriority priority
) {
}
