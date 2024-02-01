package com.aadim.project.service.impl;

import com.aadim.project.dto.request.TaskReq;
import com.aadim.project.dto.request.TaskRequest;
import com.aadim.project.dto.response.TaskResponse;
import com.aadim.project.entity.Task;
import com.aadim.project.entity.User;
import com.aadim.project.repository.InternRepository;
import com.aadim.project.repository.TaskRepository;
import com.aadim.project.repository.UserRepository;
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

    private final UserRepository userRepository;

    @Override
    public TaskResponse saveTask(TaskRequest taskRequest) {
        return null;
    }

    @Override
    public List<TaskResponse> saveAllTasks(TaskRequest taskRequest) {
        log.info("Task save request received");
        List<TaskReq> taskRequests = taskRequest.getTasks();
        List<Task> savedTasks = new ArrayList<>();

        for (TaskReq taskReq : taskRequests) {
            Task task = new Task();
            User user = userRepository.getReferenceById(taskReq.getUserId());
            task.setUser(user);
            task.setTask(taskReq.getTask());
            task.setStatus(taskReq.getStatus());
            task.setProblem(taskReq.getProblem());
            task.setTimeTaken(taskReq.getTimeTaken());

            savedTasks.add(taskRepository.save(task));
        }

        List<TaskResponse> taskResponses = new ArrayList<>();
        for (Task savedTask : savedTasks) {
            TaskResponse taskResponse = new TaskResponse();
            taskResponse.setProblem(savedTask.getProblem());
            taskResponse.setTask(savedTask.getTask());
            taskResponse.setStatus(savedTask.getStatus());
            taskResponse.setTimeTaken(savedTask.getTimeTaken());
            taskResponse.setUserId(savedTask.getUser().getId());

            taskResponses.add(taskResponse);
        }
        return taskResponses;


//        List<Task> tasks = taskRequest.getTasks();
//        List<Task> savedTasks = taskRepository.saveAll(tasks);
//        return savedTasks.stream()
//                .map(task -> modelMapper.map(task, TaskResponse.class))
//                .collect(Collectors.toList());
    }



    @Override
    public List<TaskResponse> getAllTasks() {
        log.info("Task fetch request received");
        List<Task> tasks = taskRepository.findAll();
        List<TaskResponse> taskResponses = new ArrayList<>();
        for (Task savedTask : tasks) {
            TaskResponse taskResponse = new TaskResponse();
            taskResponse.setProblem(savedTask.getProblem());
            taskResponse.setTask(savedTask.getTask());
            taskResponse.setStatus(savedTask.getStatus());
            taskResponse.setTimeTaken(savedTask.getTimeTaken());
            taskResponse.setUserId(savedTask.getUser().getId());

            taskResponses.add(taskResponse);
        }
        return taskResponses;

//        return tasks.stream()
//                .map(task -> modelMapper.map(task,TaskResponse.class))
//                .collect(Collectors.toList());

    }
    @Override
    public TaskResponse getTaskById(Integer id) {
        Task task = taskRepository.getReferenceById(id);

        TaskResponse taskResponse = new TaskResponse();
        taskResponse.setTask(task.getTask());
        taskResponse.setUserId(task.getUser().getId());
        taskResponse.setStatus(task.getStatus());
        taskResponse.setTimeTaken(task.getTimeTaken());
        taskResponse.setProblem(task.getProblem());

        return taskResponse;
    }
}
