package com.example.mytodobackend.task;

import com.example.mytodobackend.task.dto.TaskRequest;
import com.example.mytodobackend.task.dto.TaskResponse;
import com.example.mytodobackend.user.User;
//import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.security.core.Authentication;


@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "http://localhost:5173")

public class TaskController {

    private final TaskRepository repository;

    public TaskController(TaskRepository repository) {
        this.repository = repository;
    }

    // не безопасный, получаю инфу о user
    //    @GetMapping
    //    public List<Task> getAllTasks(Authentication authentication) {
    //        User user = (User) authentication.getPrincipal();
    //        return repository.findByUser(user);
    //    }

    //safer method
    @GetMapping
    public List<TaskResponse> getAllTasks(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        List<Task> tasks = repository.findByUser(user);

        return tasks.stream()
                .map(task -> new TaskResponse(
                        task.getId(),
                        task.getTitle(),
                        task.getDescription(),
                        task.getStatus(),
                        task.getPriority(),
                        task.getDeadLine(),
                        task.getTags()
                ))
                .toList();
    }

    @PostMapping
    public TaskResponse createTask(@RequestBody TaskRequest request, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        Task task = new Task();
        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setStatus(request.status());
        task.setPriority(request.priority());
        task.setDeadLine(request.deadLine());
        task.setTags(request.tags());
        task.setUser(user);

        Task saved = repository.save(task);

        return new TaskResponse(
                saved.getId(),
                saved.getTitle(),
                saved.getDescription(),
                saved.getStatus(),
                saved.getPriority(),
                saved.getDeadLine(),
                saved.getTags()
        );
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task updatedTask, Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        Task task = repository.findById(id)
                .filter(el -> el.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setStatus(updatedTask.getStatus());
        task.setPriority(updatedTask.getPriority());
        task.setDeadLine(updatedTask.getDeadLine());
        task.setTags(updatedTask.getTags());

        return repository.save(task);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        Task task = repository.findById(id)
                        .filter(el -> el.getUser().getId().equals(user.getId()))
                        .orElseThrow(() -> new RuntimeException("Task not found"));

        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
