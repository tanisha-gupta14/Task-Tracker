package com.tan.tasks.services;

import com.tan.tasks.domain.entities.Task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskService {
    List<Task> listTasks(UUID taskListId);
    Task createTask(UUID taskListId, Task task);
    Optional<Task> getTasks(UUID taskListId, UUID taskId);
    Task updateTask(UUID taskListId, UUID taskId, Task task);
    void deleteTask(UUID taskListId,UUID taskId);
}
