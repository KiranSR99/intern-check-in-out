package com.aadim.project.service;

import com.aadim.project.dto.request.TaskRequest;
import com.aadim.project.dto.response.TaskResponse;
import com.aadim.project.entity.Task;

public interface TaskService {
    TaskResponse saveTask(TaskRequest taskRequest);
}
