package com.tan.tasks.mappers.impl;

import com.tan.tasks.domain.dto.TaskListDto;
import com.tan.tasks.domain.entities.Task;
import com.tan.tasks.domain.entities.TaskList;
import com.tan.tasks.domain.entities.TaskStatus;
import com.tan.tasks.mappers.TaskListMapper;
import com.tan.tasks.mappers.TaskMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TaskListMapperImpl implements TaskListMapper {

    private final TaskMapper taskMapper;

    public TaskListMapperImpl(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    @Override
    public TaskList fromDto(TaskListDto dto) {
        TaskList list = new TaskList();
        list.setTitle(dto.title());
        list.setDescription(dto.description());

        Optional.ofNullable(dto.tasks())
                .ifPresent(tasks ->
                        list.setTasks(
                                tasks.stream()
                                        .map(taskMapper::fromDto)
                                        .toList()
                        )
                );

        return list;
    }

    @Override
    public TaskListDto toDto(TaskList taskList) {
         return new TaskListDto(
           taskList.getId(),
           taskList.getTitle(),
           taskList.getDescription(),
           Optional.ofNullable(taskList.getTasks())
                   .map(List::size)
                   .orElse(0),
           calculateTaskListProgress(taskList.getTasks()),
           Optional.ofNullable(taskList.getTasks())
                   .map(tasks ->
                        tasks.stream().map(taskMapper::toDto).toList()
                   ).orElse(null)
         );
    }

    private Double calculateTaskListProgress(List<Task>tasks){
        if(null==tasks){
            return null;
        }
        long closedTaskCount=tasks.stream().filter(task->
                        TaskStatus.CLOSED==task.getStatus()
                ).count();
        return (double) closedTaskCount/tasks.size();
    }
}
