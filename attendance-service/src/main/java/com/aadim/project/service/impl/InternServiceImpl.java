package com.aadim.project.service.impl;

import com.aadim.project.dto.request.InternRequest;
import com.aadim.project.dto.response.InternResponse;
import com.aadim.project.entity.Intern;
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
    public InternResponse saveUser(InternRequest request) {
        return null;
    }

    @Override
    public InternResponse saveIntern(InternRequest request) {
        log.info("Saving Intern");
        Intern intern = new Intern();
        intern.setFullName(request.getFullName());
        intern.setPhone(request.getPhone());
//        intern.setFieldType(request.getFieldType().toString());

        Intern savedIntern = internRepository.save(intern);
        return new InternResponse(savedIntern);
    }
}
