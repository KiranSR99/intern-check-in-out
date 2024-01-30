package com.aadim.project.service.impl;

import com.aadim.project.dto.request.InternRequest;
import com.aadim.project.dto.response.InternResponse;
import com.aadim.project.entity.FieldType;
import com.aadim.project.entity.Intern;
import com.aadim.project.entity.Supervisior;
import com.aadim.project.repository.InternRepository;
import com.aadim.project.repository.SupervisiorRepository;
import com.aadim.project.service.InternService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class InternServiceImpl implements InternService {
    private final InternRepository internRepository;
    private final SupervisiorRepository supervisiorRepository;



    @Override
    public InternResponse saveIntern(InternRequest request) {
        log.info("Saving Intern");
        Intern intern = new Intern();
        intern.setFullName(request.getFullName());
        intern.setPhone(request.getPhone());
        intern.setFieldType(FieldType.valueOf(request.getFieldType()));
       Supervisior supervisior = supervisiorRepository.findById(request.getSupervisiorId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + request.getSupervisiorId()));

        intern.setSupervisior(supervisior);

        Intern savedIntern = internRepository.save(intern);
        return new InternResponse(savedIntern);
    }
}
