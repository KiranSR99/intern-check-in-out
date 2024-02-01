package com.aadim.project.controller;

import com.aadim.project.controller.Base.BaseController;
import com.aadim.project.dto.GlobalApiResponse;
import com.aadim.project.dto.request.TaskRequest;
import com.aadim.project.dto.request.TaskUpdateRequest;
import com.aadim.project.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/task")
@RequiredArgsConstructor
public class TaskController extends BaseController {
    private final TaskService taskService;

    @PostMapping("/saveTasks")
    public ResponseEntity<GlobalApiResponse> saveAllTasks(@RequestBody TaskRequest request) {
        return successResponse(taskService.saveAllTasks(request), "Tasks saved successfully");
    }

    @GetMapping("/getAllTasks")
    public ResponseEntity<GlobalApiResponse> getAllTasks() {
        return successResponse(taskService.getAllTasks(), "Tasks fetched successfully");
    }

    @GetMapping("/getTaskById/{id}")
    public ResponseEntity<GlobalApiResponse> getTaskById(@PathVariable Integer id) {
        return successResponse(taskService.getTaskById(id), "Task with Id "+ id +" fetched successfully");
    }

    @PutMapping("/updateTask")
    public ResponseEntity<GlobalApiResponse> updateTask(@RequestBody TaskUpdateRequest updateRequest){
        return successResponse(taskService.updateTask(updateRequest), "Task Updated successfully");
    }
}
