package com.aadim.project.service.impl;

import com.aadim.project.dto.request.TaskReq;
import com.aadim.project.dto.request.TaskRequest;
import com.aadim.project.dto.request.TaskUpdateRequest;
import com.aadim.project.dto.response.TaskResponse;
import com.aadim.project.entity.Task;
import com.aadim.project.entity.User;
import com.aadim.project.repository.InternRepository;
import com.aadim.project.repository.ScheduleRepository;
import com.aadim.project.repository.TaskRepository;
import com.aadim.project.repository.UserRepository;
import com.aadim.project.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.swing.event.ListDataEvent;
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

    private final ScheduleRepository scheduleRepository;

    @Override
    public List<TaskResponse> saveAllTasks(TaskRequest taskRequest) {
        log.info("Task save request received");
//        List<TaskReq> taskRequests = taskRequest.getTasks();
        List<Task> savedTasks = new ArrayList<>();
        List<TaskResponse> taskResponses = new ArrayList<>();

        taskRequest.getTasks().forEach( t ->{
            Task task = new Task();
            BeanUtils.copyProperties(t,task);
            User user = userRepository.findById(t.getUserId()).orElseThrow(()->
                    new RuntimeException("User not found"));
            task.setUser(user);
            task.setSchedule(scheduleRepository.getLatestScheduleForTasksByInternId(internRepository.findInternByUserId(t.getUserId()).getId()));
            savedTasks.add(task);
            TaskResponse taskResponse = new TaskResponse(task);
            taskResponses.add(taskResponse);
        });

        taskRepository.saveAllAndFlush(savedTasks);
        return taskResponses;

//
//        for (TaskReq taskReq : taskRequests) {
//            Task task = new Task();
//            User user = userRepository.getReferenceById(taskReq.getUserId());
//            task.setUser(user);
//            task.setTask(taskReq.getTask());
//            task.setStatus(taskReq.getStatus());
//            task.setProblem(taskReq.getProblem());
//            task.setTimeTaken(taskReq.getTimeTaken());
//
//            savedTasks.add(taskRepository.save(task));
//            log.info("Task saved successfully");
//        }
//
//        List<TaskResponse> taskResponses = new ArrayList<>();
//        for (Task savedTask : savedTasks) {
//            TaskResponse taskResponse = new TaskResponse();
//            taskResponse.setProblem(savedTask.getProblem());
//            taskResponse.setTask(savedTask.getTask());
//            taskResponse.setStatus(savedTask.getStatus());
//            taskResponse.setTimeTaken(savedTask.getTimeTaken());
//            taskResponse.setUserId(savedTask.getUser().getId());
//
//            taskResponses.add(taskResponse);
//        }
//        return taskResponses;


    }



    @Override
    public List<TaskResponse> getAllTasks() {
        log.info("Task fetch request received");
        List<Task> tasks = taskRepository.findAll();
        List<TaskResponse> taskResponses = new ArrayList<>();
        for (Task savedTask : tasks) {
            TaskResponse taskResponse = new TaskResponse();
            taskResponse.setTaskId(savedTask.getTaskId());
            taskResponse.setProblem(savedTask.getProblem());
            taskResponse.setTask(savedTask.getTask());
            taskResponse.setStatus(savedTask.getStatus());
            taskResponse.setTimeTaken(savedTask.getTimeTaken());
            taskResponse.setUserId(savedTask.getUser().getId());

            taskResponses.add(taskResponse);
            log.info("Task fetched request successfully");
        }
        return taskResponses;

//        return tasks.stream()
//                .map(task -> modelMapper.map(task,TaskResponse.class))
//                .collect(Collectors.toList());

    }
    @Override
    public TaskResponse getTaskById(Integer id) {
        log.info("Task fetchById request received");

        Task task = taskRepository.getReferenceById(id);

        TaskResponse taskResponse = new TaskResponse();
        taskResponse.setTaskId(task.getTaskId());
        taskResponse.setTask(task.getTask());
        taskResponse.setUserId(task.getUser().getId());
        taskResponse.setStatus(task.getStatus());
        taskResponse.setTimeTaken(task.getTimeTaken());
        taskResponse.setProblem(task.getProblem());

        log.info("Task fetchById successful");
        return taskResponse;
    }

    @Override
    public List<TaskResponse> getTaskOfOneUser(Integer userId) {
        List<Task> userTasks = taskRepository.findByUserId(userId);

        List<TaskResponse> taskResponses = userTasks.stream()
                .map(TaskResponse::new)
                .toList();

        return taskResponses;
    }

    @Override
    public TaskResponse updateTask(TaskUpdateRequest updateRequest) {
        log.info("Task update request received");
        Task task = taskRepository.getReferenceById(updateRequest.getTaskId());

        task.setStatus(updateRequest.getStatus());
        task.setTask(updateRequest.getTask());
        task.setProblem(updateRequest.getProblem());
        task.setTimeTaken(updateRequest.getTimeTaken());

        Task savedTask = taskRepository.save(task);
        log.info("Task updated successfully");
        return new TaskResponse(savedTask);
    }

}
