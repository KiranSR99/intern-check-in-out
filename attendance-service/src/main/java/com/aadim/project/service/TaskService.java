package com.aadim.project.service;

import com.aadim.project.dto.request.TaskRequest;
import com.aadim.project.dto.request.TaskUpdateRequest;
import com.aadim.project.dto.response.TaskResponse;
import com.aadim.project.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskService {

    List<TaskResponse> saveAllTasks(TaskRequest  taskRequest);

    Page<TaskResponse> getAllTasks(Pageable pageable);

    TaskResponse getTaskById(Integer id);

    List<TaskResponse> getTaskOfOneUser(Integer userId);

    TaskResponse updateTask(TaskUpdateRequest updateRequest);

    List<Task> searchTasks(LocalDateTime localDateTime);
}
