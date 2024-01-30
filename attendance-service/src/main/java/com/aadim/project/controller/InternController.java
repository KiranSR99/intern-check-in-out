package com.aadim.project.controller;

import com.aadim.project.controller.Base.BaseController;
import com.aadim.project.dto.GlobalApiResponse;
import com.aadim.project.dto.request.InternRequest;
import com.aadim.project.service.InternService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class InternController extends BaseController {
private final InternService internService;

    @PostMapping("/intern")
    public ResponseEntity<GlobalApiResponse> saveIntern(@RequestBody InternRequest request) {
        return successResponse(internService.saveIntern(request));
    }

}
