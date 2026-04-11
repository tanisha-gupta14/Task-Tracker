package com.tan.tasks.services.impl;

import com.tan.tasks.auth.entity.User;
import com.tan.tasks.auth.security.UserPrincipal;
import com.tan.tasks.domain.entities.TaskList;
import com.tan.tasks.repositories.TaskListRepository;
import com.tan.tasks.services.TaskListService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskListServiceImpl implements TaskListService {

    private final TaskListRepository taskListRepository;

    public TaskListServiceImpl(TaskListRepository taskListRepository) {
        this.taskListRepository = taskListRepository;
    }

    @Override
    public List<TaskList> listTaskLists() {
        User user = getCurrentUser();
        return taskListRepository.findByUser(user);

    }

    @Override
    public TaskList createTaskList(TaskList taskList) {
        if (taskList.getId() != null) {
            throw new IllegalArgumentException("task list already has an id!");
        }
        if (taskList.getTitle() == null || taskList.getTitle().isBlank()) {
            throw new IllegalArgumentException("task list title must be present");
        }

        User user = getCurrentUser();
        LocalDateTime now = LocalDateTime.now();

        TaskList newList = new TaskList();
        newList.setTitle(taskList.getTitle());
        newList.setDescription(taskList.getDescription());
        newList.setUser(user);
        newList.setCreated(now);
        newList.setUpdated(now);

        return taskListRepository.save(newList);
    }

    @Override
    public Optional<TaskList> getTaskList(UUID id) {
        User user = getCurrentUser();
        return taskListRepository.findByIdAndUser(id, user);
    }

    @Transactional
    @Override
    public TaskList updateTaskList(UUID taskListId, TaskList taskList) {
        if(null==taskList.getId()){
            throw new IllegalArgumentException("Task list must have an ID");
        }
        if(!Objects.equals(taskList.getId(),taskListId)){
            throw new IllegalArgumentException("attempting to chanfe task id, this is not permitted");
        }
        User user = getCurrentUser();

        TaskList existingTaskList = taskListRepository
                .findByIdAndUser(taskListId, user)
                .orElseThrow(() -> new IllegalArgumentException("task list not found"));

        existingTaskList.setTitle(taskList.getTitle());
        existingTaskList.setDescription(taskList.getDescription());
        existingTaskList.setUpdated(LocalDateTime.now());
        return taskListRepository.save(existingTaskList);

    }

    @Override
    public void deleteTaskList(UUID taskListId) {
        User user = getCurrentUser();
        TaskList list = taskListRepository
                .findByIdAndUser(taskListId, user)
                .orElseThrow(() -> new IllegalArgumentException("task list not found"));

        taskListRepository.delete(list);
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
        return principal.getUser();
    }
}
