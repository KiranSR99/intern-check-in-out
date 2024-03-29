package com.aadim.project.controller;

import com.aadim.project.controller.Base.BaseController;
import com.aadim.project.dto.GlobalApiResponse;
import com.aadim.project.dto.request.TaskRequest;
import com.aadim.project.dto.request.TaskUpdateRequest;
import com.aadim.project.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


@RestController
@RequestMapping("api/v1/task")
@RequiredArgsConstructor
public class TaskController extends BaseController {
    private final TaskService taskService;

    @PreAuthorize("hasAuthority('INTERN')")
    @PostMapping("/saveTasks")
    public ResponseEntity<GlobalApiResponse> saveAllTasks(@RequestBody TaskRequest request) {
        return successResponse(taskService.saveAllTasks(request), "Tasks saved successfully");
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','INTERN','SUPERVISOR')")
    @GetMapping("/getAllTasks")
    public ResponseEntity<GlobalApiResponse> getAllTasks(@PageableDefault Pageable pageable) {
        return successResponse(taskService.getAllTasks(pageable), "Tasks fetched successfully");
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','INTERN','SUPERVISOR')")
    @GetMapping("/getTaskById/{id}")
    public ResponseEntity<GlobalApiResponse> getTaskById(@PathVariable Integer id) {
        return successResponse(taskService.getTaskById(id), "Task with Id "+ id +" fetched successfully");
    }

    @GetMapping("/getTaskOfOneUser/{id}")
    public ResponseEntity<GlobalApiResponse> getTaskOfOneUser(@PageableDefault Pageable pageable,@PathVariable Integer id){
        return successResponse(taskService.getTaskOfOneUser(pageable,id), "Task of one user fetched successfully");
    }

    @PreAuthorize("hasAuthority('INTERN')")
    @PutMapping("/updateTask")
    public ResponseEntity<GlobalApiResponse> updateTask(@RequestBody TaskUpdateRequest updateRequest){
        return successResponse(taskService.updateTask(updateRequest), "Task Updated successfully");
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','INTERN','SUPERVISOR')")
    @GetMapping("/searchTaskByDate")
    public ResponseEntity<GlobalApiResponse> searchTaskByDate(@RequestParam("date") LocalDateTime localDateTime) {
        return successResponse(taskService.searchTasks(localDateTime), "Tasks searched successfully");
    }
}
