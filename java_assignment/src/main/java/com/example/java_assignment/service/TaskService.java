package com.example.java_assignment.service;

import com.example.java_assignment.dto.TaskRequestDTO;
import com.example.java_assignment.dto.TaskResponseDTO;
import com.example.java_assignment.exception.ResourceNotFoundException;
import com.example.java_assignment.mapper.TaskMapper;
import com.example.java_assignment.model.Admin;
import com.example.java_assignment.model.AppUser;
import com.example.java_assignment.model.Task;
import com.example.java_assignment.repository.AdminRepository;
import com.example.java_assignment.repository.AppUserRepository;
import com.example.java_assignment.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final AppUserRepository userRepository;
    private final AdminRepository adminRepository;

    public List<TaskResponseDTO> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(TaskMapper::toDTO)
                .collect(Collectors.toList());
    }

    public TaskResponseDTO createTask(TaskRequestDTO dto) {
        AppUser user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Admin admin = adminRepository.findById(dto.getAdminId())
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found"));

        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setAssignedTo(user);
        task.setAssignedBy(admin);

        return TaskMapper.toDTO(taskRepository.save(task));
    }

    public void updateTask(Long id, TaskRequestDTO dto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        AppUser user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Admin admin = adminRepository.findById(dto.getAdminId())
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found"));

        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setAssignedTo(user);
        task.setAssignedBy(admin);

        TaskMapper.toDTO(taskRepository.save(task));
    }

    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        taskRepository.delete(task);
    }
}

