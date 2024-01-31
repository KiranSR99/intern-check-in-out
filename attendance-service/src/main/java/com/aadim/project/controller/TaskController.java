package com.aadim.project.controller;

import com.aadim.project.controller.Base.BaseController;
import com.aadim.project.dto.GlobalApiResponse;
import com.aadim.project.dto.request.TaskRequest;
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

    @PostMapping("/check")
    public String saveAllTasks(@RequestBody String name){
        return name;
    }

    @GetMapping("/getAllTasks")
    public ResponseEntity<GlobalApiResponse> getAllTasks() {
        return successResponse(taskService.getAllTasks(), "Tasks fetched successfully");
    }

}
