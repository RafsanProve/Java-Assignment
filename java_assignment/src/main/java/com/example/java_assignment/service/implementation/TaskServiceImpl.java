package com.example.java_assignment.service.implementation;

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
import com.example.java_assignment.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    @Autowired
    private final TaskRepository taskRepository;

    @Autowired
    private final AppUserRepository userRepository;

    @Autowired
    private final AdminRepository adminRepository;

    public List<TaskResponseDTO> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(TaskMapper::toDTO)
                .collect(Collectors.toList());
    }

    public TaskResponseDTO createTask(TaskRequestDTO dto, long adminId) {
        AppUser user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found"));

        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setAssignedTo(user);
        task.setAssignedBy(admin);

        return TaskMapper.toDTO(taskRepository.save(task));
    }

    public void updateTask(Long id, TaskRequestDTO dto, Long adminId) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        AppUser user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Admin admin = adminRepository.findById(adminId)
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

    @Override
    public Boolean isAdminOwner(Long id, Long adminId) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        return task.getAssignedBy().getId().equals(adminId);
    }
}
