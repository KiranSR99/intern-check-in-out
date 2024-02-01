package com.aadim.project.service;

import com.aadim.project.dto.request.TaskRequest;
import com.aadim.project.dto.request.TaskUpdateRequest;
import com.aadim.project.dto.response.TaskResponse;

import java.util.List;

public interface TaskService {

    List<TaskResponse> saveAllTasks(TaskRequest  taskRequest);

    List<TaskResponse> getAllTasks();

    TaskResponse getTaskById(Integer id);

    TaskResponse updateTask(TaskUpdateRequest updateRequest);
}
