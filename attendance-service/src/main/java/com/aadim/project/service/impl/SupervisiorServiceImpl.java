package com.aadim.project.service.impl;

import com.aadim.project.dto.request.SupervisiorRequest;
import com.aadim.project.dto.response.SupervisiorResponse;
import com.aadim.project.entity.Supervisior;
import com.aadim.project.repository.SupervisiorRepository;
import com.aadim.project.service.SupervisiorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SupervisiorServiceImpl implements SupervisiorService {
    @Autowired
    private SupervisiorRepository supervisiorRepository;
    @Override
    public SupervisiorResponse addSupervisior(SupervisiorRequest supervisiorResponse) {
        Supervisior supervisior = new Supervisior();
        supervisior.setFullName(supervisiorResponse.getFullName());
        supervisior.setPhone(supervisiorResponse.getPhone());
        return new SupervisiorResponse(supervisiorRepository.save(supervisior));
    }

    @Override
    public List<SupervisiorResponse> getSupervisior() {
        List<SupervisiorResponse> supervisiorResponseArrayList = new ArrayList<>();
        List<Supervisior> supervisiorList = supervisiorRepository.findAll();

        for(Supervisior supervisior: supervisiorList){
            supervisiorResponseArrayList.add(new SupervisiorResponse(supervisior));
        }

        return supervisiorResponseArrayList;
    }

    @Override
    public SupervisiorResponse getSupervisiorById(Integer id) {
        Supervisior supervisior = supervisiorRepository.findById(id).get();
        return new SupervisiorResponse(supervisior);
    }
}
