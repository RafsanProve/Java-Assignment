package com.example.java_assignment.mapper;

import com.example.java_assignment.dto.TaskResponseDTO;
import com.example.java_assignment.model.Task;

public class TaskMapper {

    public static TaskResponseDTO toDTO(Task task) {
        return new TaskResponseDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getAssignedTo().getUsername(),
                task.getAssignedBy().getUsername(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }
}

