package com.aadim.project.controller;

import com.aadim.project.controller.Base.BaseController;
import com.aadim.project.dto.GlobalApiResponse;
import com.aadim.project.dto.request.TaskRequest;
import com.aadim.project.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/task")
@RequiredArgsConstructor
public class TaskController extends BaseController {
    private final TaskService taskService;

    @PostMapping("/saveTask")
    private ResponseEntity<GlobalApiResponse> saveTask(@RequestBody TaskRequest request){
        return successResponse(taskService.saveTask(request), "Task saved succesfully");
    }

}
