package com.trendmicro.on_boarding_web_application.controller;

import com.trendmicro.on_boarding_web_application.entity.Task;
import com.trendmicro.on_boarding_web_application.entity.UserTask;
import com.trendmicro.on_boarding_web_application.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    // GET ALL USERS TASKS
    @GetMapping("{userId}")
    public ResponseEntity<List<UserTask>> getAllUserTasks(@PathVariable(name = "userId") UUID userId) {
        return ResponseEntity.status(200).body(taskService.getAllUserTasks(userId));
    }

    // CREATE USER TASKS PER USER
    @PostMapping("/create/{userId}")
    public ResponseEntity<UserTask> createUserTask(@PathVariable(name = "userId") UUID userId, @RequestBody Task task) {
        return ResponseEntity.status(201).body(taskService.createUserTask(userId, task));
    }

    // UPDATE USER TASK STATUS
    @PutMapping("/update/{userId}")
    public ResponseEntity<UserTask> updateUserTask(@RequestBody UserTask userTask, @PathVariable(name = "userId") UUID userId, @RequestParam UUID userTaskId) {
        return ResponseEntity.status(200).body(taskService.updateUserTask(userTask, userId ,userTaskId));
    }

    // DELETE USER TASKS
    @DeleteMapping("/delete/{userTaskId}")
    public ResponseEntity<Void> deleteTaskById(@PathVariable(name = "userTaskId") UUID userTaskId) {
        taskService.deleteTaskById(userTaskId);
        return ResponseEntity.status(204).build();
    }

    // GET ALL MANDATORY TASKS
    @GetMapping("/mandatory/{userId}")
    public ResponseEntity<List<UserTask>> getAllMandatoryTask(@PathVariable(name = "userId") UUID userId) {
        return ResponseEntity.status(200).body(taskService.getAllUserMandatoryTask(userId));
    }

    // GET ALL PERSONAL TASK
    @GetMapping("/personal/{userId}")
    public ResponseEntity<List<UserTask>> getAllPersonalTask(@PathVariable(name = "userId") UUID userId) {
        return ResponseEntity.status(200).body(taskService.getAllUserPersonalTask(userId));
    }

}
