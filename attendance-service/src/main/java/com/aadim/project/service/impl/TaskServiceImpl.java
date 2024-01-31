package com.aadim.project.service.impl;

import com.aadim.project.dto.request.TaskRequest;
import com.aadim.project.dto.response.TaskResponse;
import com.aadim.project.entity.Task;
import com.aadim.project.repository.TaskRepository;
import com.aadim.project.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final ModelMapper modelMapper;

//    @Override
//    public TaskResponse saveTask(List<TaskRequest> taskRequests) {
//        log.info("Task save request received");
//
//        List<TaskRequest> savedTask =  taskRepository.saveAll(taskRequests);
//        log.info("Task saved");
//
//        return new TaskResponse(savedTask);
//    }

    @Override
    public TaskResponse saveTask(TaskRequest taskRequest) {
        return null;
    }

    @Override
    public List<TaskResponse> saveAllTasks(List<TaskRequest> taskRequests) {
        log.info("Task save request received");
        List<Task> tasks = taskRequests.stream()
                .map(taskRequest -> modelMapper.map(taskRequest, Task.class))
                .collect(Collectors.toList());

        List<Task> savedTasks = taskRepository.saveAll(tasks);

        return savedTasks.stream()
                .map(savedTask -> modelMapper.map(savedTask, TaskResponse.class))
                .collect(Collectors.toList());
    }
}
