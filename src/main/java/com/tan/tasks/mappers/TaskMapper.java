package com.tan.tasks.mappers;

import com.tan.tasks.domain.dto.TaskDto;
import com.tan.tasks.domain.entities.Task;

public interface TaskMapper {

    Task fromDto(TaskDto taskDto);

    TaskDto toDto(Task task);
}
