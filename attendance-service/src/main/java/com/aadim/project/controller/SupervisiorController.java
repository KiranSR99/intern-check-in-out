package com.aadim.project.controller;

import com.aadim.project.controller.Base.BaseController;
import com.aadim.project.dto.GlobalApiResponse;
import com.aadim.project.dto.request.SupervisiorRequest;
import com.aadim.project.dto.response.SupervisiorResponse;
import com.aadim.project.service.impl.SupervisiorServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/supervisior")
public class SupervisiorController extends BaseController {

    @Autowired
    private SupervisiorServiceImpl supervisiorService;

    @PostMapping("/add")
    public ResponseEntity<GlobalApiResponse> addSupervisior(SupervisiorRequest supervisiorRequest) {

        return successResponse(supervisiorService.addSupervisior(supervisiorRequest));
    }


    @GetMapping("/get")
    public ResponseEntity<GlobalApiResponse> getSupervisior() {
        return successResponse(supervisiorService.getSupervisior());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<GlobalApiResponse> getSupervisiorById(@PathVariable Integer id) {
        return successResponse(supervisiorService.getSupervisiorById(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GlobalApiResponse> deleteSupervisiorById(@PathVariable Integer id) {
        return successResponse(supervisiorService.deleteSupervisiorById(id));
    }
}
