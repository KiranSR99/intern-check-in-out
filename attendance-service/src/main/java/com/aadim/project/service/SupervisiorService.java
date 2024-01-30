package com.aadim.project.service;

import com.aadim.project.dto.request.SupervisiorRequest;
import com.aadim.project.dto.response.SupervisiorResponse;

import java.util.List;

public interface SupervisiorService {
    SupervisiorResponse addSupervisior(SupervisiorRequest supervisiorResponse);

    List<SupervisiorResponse> getSupervisior();

    SupervisiorResponse getSupervisiorById(Integer id);
}
