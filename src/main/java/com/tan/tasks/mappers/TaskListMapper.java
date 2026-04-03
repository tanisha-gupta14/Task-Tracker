package com.tan.tasks.mappers;

import com.tan.tasks.domain.dto.TaskListDto;
import com.tan.tasks.domain.entities.TaskList;

public interface TaskListMapper {

    TaskList fromDto(TaskListDto taskListDto);

    TaskListDto toDto(TaskList taskList);

}
