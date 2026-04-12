package com.tan.tasks.admin;

import com.tan.tasks.admin.dto.AdminTaskDto;
import com.tan.tasks.admin.dto.AdminTaskListDto;
import com.tan.tasks.admin.dto.AdminUserDto;
import com.tan.tasks.auth.entity.Role;
import com.tan.tasks.auth.entity.User;
import com.tan.tasks.auth.repository.UserRepository;
import com.tan.tasks.domain.entities.Task;
import com.tan.tasks.domain.entities.TaskStatus;
import com.tan.tasks.repositories.TaskListRepository;
import com.tan.tasks.repositories.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserRepository userRepo;
    private final TaskListRepository taskListRepo;
    private final TaskRepository taskRepo;

    public AdminController(
            UserRepository userRepo,
            TaskListRepository taskListRepo,
            TaskRepository taskRepo
    ) {
        this.userRepo = userRepo;
        this.taskListRepo = taskListRepo;
        this.taskRepo = taskRepo;
    }


    @GetMapping("/users")
    public Page<AdminUserDto> listUsers(Pageable pageable) {
        return userRepo.findByRole(Role.USER, pageable)
                .map(u -> new AdminUserDto(u.getEmail()));
    }


    @GetMapping("/users/{email}/task-lists")
    public List<AdminTaskListDto> userTaskLists(@PathVariable String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow();

        return taskListRepo.findByUser(user)
                .stream()
                .map(tl -> new AdminTaskListDto(
                        tl.getId(),
                        tl.getTitle(),
                        tl.getDescription(),
                        calculateTaskListProgress(tl.getTasks())
                ))
                .toList();
    }

    private Double calculateTaskListProgress(List<Task> tasks) {
        if (tasks == null || tasks.isEmpty()) {
            return 0.0;
        }
        long closed = tasks.stream()
                .filter(t -> t.getStatus() == TaskStatus.CLOSED)
                .count();

        return (double) closed / tasks.size();
    }

    @GetMapping("/task-lists/{taskListId}/tasks")
    public List<AdminTaskDto> tasks(@PathVariable UUID taskListId) {
        System.out.println("endpoint hit");
        return taskRepo.findByTaskListId(taskListId)
                .stream()
                .map(t -> new AdminTaskDto(
                        t.getId(),
                        t.getTitle(),
                        t.getStatus(),
                        t.getPriority()
                ))
                .toList();
    }
}