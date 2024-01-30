package com.aadim.project.controller;

import com.aadim.project.dto.GlobalApiResponse;
import com.aadim.project.dto.request.TaskRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/task")
@RequiredArgsConstructor
public class TaskController {

    @PostMapping("/saveTask")
    private ResponseEntity<GlobalApiResponse> saveTask(@RequestBody TaskRequest request){
        return
    }
}
