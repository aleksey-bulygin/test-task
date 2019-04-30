package com.test.services;

import com.test.entity.Task;

import java.util.Optional;
import java.util.UUID;

public interface TaskService {

    Task saveTask(Task task);

    Optional<Task> getTaskById(UUID uuid);

    void runTask(Task task);
}
