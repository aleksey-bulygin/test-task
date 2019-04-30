package com.test.services;

import com.test.entity.Task;
import com.test.repositories.TaskRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

@Service("TaskService")
public class TaskServiceImpl implements TaskService {

    private final DateTimeFormatter dtf = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    private final TaskRepositories taskRepositories;

    @Autowired
    public TaskServiceImpl(TaskRepositories taskRepositories, PlatformTransactionManager transactionManager) {
        this.taskRepositories = taskRepositories;
    }

    @Override
    public Task saveTask(Task task) {
        return taskRepositories.save(task);
    }

    @Override
    public Optional<Task> getTaskById(UUID uuid) {
        return taskRepositories.findById(uuid);
    }

    @Async("asyncExecutor")
    @Override
    public void runTask(Task task) {
        task.setStatus("running");
        task.setTimestamp(OffsetDateTime.now().format(dtf));
        taskRepositories.save(task);

        try {
            Thread.sleep(120000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            task.setStatus("finished");
            task.setTimestamp(OffsetDateTime.now().format(dtf));
            System.out.println(task.toString() + Thread.currentThread().getName());
            taskRepositories.save(task);
        }

    }

}
