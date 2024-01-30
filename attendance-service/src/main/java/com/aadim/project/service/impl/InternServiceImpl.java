package com.aadim.project.service.impl;

import com.aadim.project.dto.request.InternRequest;
import com.aadim.project.dto.response.InternResponse;
import com.aadim.project.repository.InternRepository;
import com.aadim.project.service.InternService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class InternServiceImpl implements InternService {
    private final InternRepository internRepository;



    @Override
    public InternResponse saveIntern(InternRequest request) {
        log.info("Saving Intern");

        return null;
    }
}
