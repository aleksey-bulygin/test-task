package com.test.controllers;

import com.test.entity.Task;
import com.test.exceptions.NotFoundException;
import com.test.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@RestController
@RequestMapping("/task")
public class TaskController {
    private final DateTimeFormatter dtf = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, consumes = {"application/json"})
    public ResponseEntity<Task> showTask(@PathVariable("id") UUID uuid) {
        return taskService.getTaskById(uuid).map(task -> new ResponseEntity<Task>(task, HttpStatus.OK))
                .orElseThrow( () -> new NotFoundException("404 not found 404"));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<UUID> setUpTask() {

        Task task = new Task();

        UUID uuid = UUID.randomUUID();

        task.setId(uuid);
        task.setStatus("created");
        task.setTimestamp(OffsetDateTime.now().format(dtf));

        taskService.saveTask(task);

        taskService.runTask(task);

        return new ResponseEntity<UUID>(uuid, HttpStatus.ACCEPTED);
    }
}
