package com.aadim.project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupervisorResponse {
    private SupervisorInfoResponse supervisorInfoResponse;
    private List<InternInfoResponse> internInfoResponses;
}
