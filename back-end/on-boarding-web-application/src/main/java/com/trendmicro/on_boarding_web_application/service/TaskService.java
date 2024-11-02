package com.trendmicro.on_boarding_web_application.service;

import com.trendmicro.on_boarding_web_application.entity.*;
import com.trendmicro.on_boarding_web_application.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final UserTaskRepository userTaskRepository;

    // UPDATE USER TASK'S STATUS
    public UserTask updateUserTask(UserTask userTaskEntity, UUID userId, UUID userTaskId) {
        Optional<UserTask> userTask = userTaskRepository.findById(userTaskId);
        if(userTask.isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), String.format("No task with id:%s is found", userTaskId));
        }
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        userTaskEntity.setUserTasksId(userTaskId);

        Task task = userTask.get().getTask();
        task.setTaskId(userTask.get().getTask().getTaskId());
        task.setIsMandatory(userTaskEntity.getTask().getIsMandatory());
        task.setTitle(userTaskEntity.getTask().getTitle());
        task.setDescription(userTaskEntity.getTask().getDescription());

        userTaskEntity.setTask(task);

        if(userTaskEntity.getStatus().equals("DONE")) {
            userTaskEntity.setDateFinished(LocalDate.now());
        } else {
            userTaskEntity.setDateFinished(null);
        }

        userTaskEntity.setDateCreated(userTask.get().getDateCreated());
        userTaskEntity.setUser(user);
        return userTaskRepository.save(userTaskEntity);
    }

    // DELETE PERSONAL TASKS
    public void deleteTaskById(UUID userTaskId) {
        Optional<UserTask> userTask = userTaskRepository.findById(userTaskId);
        if(userTask.isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), String.format("No task with id:%s is found", userTaskId));
        }
        if(userTask.get().getTask().getIsMandatory().equals(true)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), String.format("Task is mandatory it cannot be deleted", userTaskId));
        }
        userTaskRepository.delete(userTask.get());
        taskRepository.delete(userTask.get().getTask());
    }

    // ASSIGN MANDATORY TASKS TO USER
    public void assignMandatoryTasksToUser(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        List<Task> mandatoryTasks = taskRepository.findByIsMandatoryTrue();

        for (Task task : mandatoryTasks) {
            UserTask userTask = new UserTask();
            userTask.setUser(user);
            userTask.setTask(task);
            userTaskRepository.save(userTask);
        }
    }

    // CREATE PERSONAL TASK
    public UserTask createUserTask(UUID userId, Task task) {
        if(task.getTitle().isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Task title is empty");
        }
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        Task saveTask = taskRepository.save(task);

        UserTask userTask = new UserTask();
        userTask.setUser(user);
        userTask.setTask(saveTask);

        return userTaskRepository.save(userTask);
    }

    // GET ALL USER'S TASKS BY USER'S ID
    public List<UserTask> getAllUserTasks(UUID userId) {
        return userTaskRepository.findAllTasksByUserId(userId);
    }

    // GET ALL USERS MANDATORY TASKS
    public List<UserTask> getAllUserMandatoryTask(UUID userId) {
        return userTaskRepository.findAllMandatoryTasksByUserId(userId);
    }

    // GET ALL USERS PERSONAL TASKS
    public List<UserTask> getAllUserPersonalTask(UUID userId) {
        return userTaskRepository.findAllPersonalTasksByUserId(userId);
    }
}
