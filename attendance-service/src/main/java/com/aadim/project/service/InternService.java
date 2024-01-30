package com.aadim.project.service;

import com.aadim.project.dto.request.InternRequest;
import com.aadim.project.dto.response.InternResponse;
import org.springframework.http.ResponseEntity;

public interface InternService {

    InternResponse saveIntern(InternRequest request);
}
