package com.aadim.project.service;

import com.aadim.project.dto.request.SupervisorRequest;
import com.aadim.project.dto.response.SupervisorResponse;

import java.util.List;

public interface SupervisorService {
    SupervisorResponse addSupervisor(SupervisorRequest supervisiorResponse);

    List<SupervisorResponse> getSupervisor();

    SupervisorResponse getSupervisorById(Integer id);

    SupervisorResponse deleteSupervisorById(Integer id);
}
