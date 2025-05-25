package com.example.java_assignment.controller;

import com.example.java_assignment.dto.TaskRequestDTO;
import com.example.java_assignment.dto.TaskResponseDTO;
import com.example.java_assignment.security.AuthUtils;
import com.example.java_assignment.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.access.AccessDeniedException;
import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@Tag(name = "Tasks", description = "Manage user-assigned tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    private HttpServletRequest request;

    @Operation(summary = "Get all tasks", description = "Returns a list of all tasks assigned to users.")
    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @Operation(summary = "Create a new task")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Task created successfully"),
            @ApiResponse(responseCode = "404", description = "User or Admin not found")
    })
    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(@Valid @RequestBody TaskRequestDTO dto) {
        if (!AuthUtils.isAdmin(request)) throw new AccessDeniedException("Only admins can create tasks");

        TaskResponseDTO createdTask = taskService.createTask(dto);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @Operation(summary = "Update a task by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task updated successfully"),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTask(@PathVariable Long id, @Valid @RequestBody TaskRequestDTO dto) {
        if (!AuthUtils.isAdmin(request)) throw new AccessDeniedException("Only admins can update tasks");

        taskService.updateTask(id, dto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete a task by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        if (!AuthUtils.isAdmin(request)) throw new AccessDeniedException("Only admins can delete tasks");

        taskService.deleteTask(id);
        return ResponseEntity.ok().build();
    }
}

