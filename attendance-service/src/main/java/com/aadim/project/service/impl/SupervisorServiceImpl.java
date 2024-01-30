package com.aadim.project.service.impl;

import com.aadim.project.dto.request.SupervisorRequest;
import com.aadim.project.dto.response.SupervisorResponse;
import com.aadim.project.entity.Supervisor;
import com.aadim.project.repository.SupervisorRepository;
import com.aadim.project.service.SupervisorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SupervisorServiceImpl implements SupervisorService {
    @Autowired
    private SupervisorRepository supervisorRepository;
    @Override
    public SupervisorResponse addSupervisor(SupervisorRequest supervisorResponse) {
        Supervisor supervisor = new Supervisor();
        supervisor.setFullName(supervisorResponse.getFullName());
        supervisor.setPhone(supervisorResponse.getPhone());
        return new SupervisorResponse(supervisorRepository.save(supervisor));
    }

    @Override
    public List<SupervisorResponse> getSupervisor() {
        List<SupervisorResponse> supervisorResponseArrayList = new ArrayList<>();
        List<Supervisor> supervisorList = supervisorRepository.findAll();

        for(Supervisor supervisor : supervisorList){
            supervisorResponseArrayList.add(new SupervisorResponse(supervisor));
        }

        return supervisorResponseArrayList;
    }

    @Override
    public SupervisorResponse getSupervisorById(Integer id) {
        Supervisor supervisor = supervisorRepository.getReferenceById(id);
        return new SupervisorResponse(supervisor);
    }

    @Override
    public SupervisorResponse deleteSupervisorById(Integer id) {
        Supervisor supervisor = supervisorRepository.getReferenceById(id);
        supervisorRepository.delete(supervisor);
        return new SupervisorResponse(supervisor);
    }
}
