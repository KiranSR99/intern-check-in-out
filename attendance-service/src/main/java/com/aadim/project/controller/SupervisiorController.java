package com.aadim.project.controller;

import com.aadim.project.dto.request.SupervisiorRequest;
import com.aadim.project.dto.response.SupervisiorResponse;
import com.aadim.project.service.impl.SupervisiorServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/supervisior")
public class SupervisiorController {

    @Autowired
    private SupervisiorServiceImpl supervisiorService;

    @PostMapping("/add")
    public SupervisiorResponse addSupervisior(SupervisiorRequest supervisiorRequest) {

        return supervisiorService.addSupervisior(supervisiorRequest);
    }


    @GetMapping("/get")
    public List<SupervisiorResponse> getSupervisior() {
        return supervisiorService.getSupervisior();
    }

    @GetMapping("/get/{id}")
    public SupervisiorResponse getSupervisiorById(@PathVariable Integer id) {
        return supervisiorService.getSupervisiorById(id);
    }
}
