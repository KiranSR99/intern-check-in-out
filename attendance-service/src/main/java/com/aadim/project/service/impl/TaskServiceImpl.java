package com.aadim.project.service.impl;

import com.aadim.project.dto.request.TaskRequest;
import com.aadim.project.dto.response.TaskResponse;
import com.aadim.project.entity.Task;
import com.aadim.project.repository.TaskRepository;
import com.aadim.project.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    public TaskResponse saveTask(TaskRequest taskRequest) {
        Task task = new Task();
        task.setTask(taskRequest.getTask());
        task.setProblem(taskRequest.getProblem());
        task.setStatus(taskRequest.getStatus());
        task.setTimeTaken(taskRequest.getTimeTaken());

        Task savedTask = taskRepository.save(task);

        return new TaskResponse(savedTask);
    }
}
