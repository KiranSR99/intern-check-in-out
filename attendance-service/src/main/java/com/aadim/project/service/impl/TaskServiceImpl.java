package com.aadim.project.service.impl;

import com.aadim.project.dto.request.TaskRequest;
import com.aadim.project.dto.response.TaskResponse;
import com.aadim.project.entity.Task;
import com.aadim.project.repository.InternRepository;
import com.aadim.project.repository.TaskRepository;
import com.aadim.project.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final ModelMapper modelMapper;

    private final InternRepository internRepository;

    @Override
    public TaskResponse saveTask(TaskRequest taskRequest) {
        return null;
    }

    @Override
    public List<TaskResponse> saveAllTasks(TaskRequest taskRequest) {
        log.info("Task save request received");
        List<Task> tasks = taskRequest.getTasks();
        List<Task> savedTasks = taskRepository.saveAll(tasks);
        return savedTasks.stream()
                .map(task -> modelMapper.map(task, TaskResponse.class))
                .collect(Collectors.toList());
}



    @Override
    public List<TaskResponse> getAllTasks() {
        log.info("Task fetch request received");
        List<Task> tasks = taskRepository.findAll();

        return tasks.stream()
                .map(task -> modelMapper.map(task,TaskResponse.class))
                .collect(Collectors.toList());
    }


}
