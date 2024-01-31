package com.aadim.project.service;

import com.aadim.project.dto.request.TaskRequest;
import com.aadim.project.dto.response.TaskResponse;

import java.util.List;

public interface TaskService {
    TaskResponse saveTask(TaskRequest taskRequest);

    List<TaskResponse> saveAllTasks(List<TaskRequest>  taskRequest);
}
