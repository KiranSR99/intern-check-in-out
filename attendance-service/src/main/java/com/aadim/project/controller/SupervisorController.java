package com.aadim.project.controller;

import com.aadim.project.controller.Base.BaseController;
import com.aadim.project.dto.GlobalApiResponse;
import com.aadim.project.dto.request.SupervisorRequest;
import com.aadim.project.service.impl.SupervisorServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/supervisor")
public class SupervisorController extends BaseController {

    @Autowired
    private SupervisorServiceImpl supervisorService;

    @PostMapping("/add")
    public ResponseEntity<GlobalApiResponse> addSupervisior(SupervisorRequest supervisorRequest) {

        return successResponse(supervisorService.addSupervisor(supervisorRequest));
    }


    @GetMapping("/get")
    public ResponseEntity<GlobalApiResponse> getSupervisior() {
        return successResponse(supervisorService.getSupervisor());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<GlobalApiResponse> getSupervisiorById(@PathVariable Integer id) {
        return successResponse(supervisorService.getSupervisorById(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GlobalApiResponse> deleteSupervisiorById(@PathVariable Integer id) {
        return successResponse(supervisorService.deleteSupervisorById(id));
    }
}
