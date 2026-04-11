package com.tan.tasks.repositories;

import com.tan.tasks.auth.entity.User;
import com.tan.tasks.domain.entities.TaskList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskListRepository extends JpaRepository<TaskList, UUID> {
    List<TaskList> findByUser(User user);

    Optional<TaskList> findByIdAndUser(UUID id, User user);
}
