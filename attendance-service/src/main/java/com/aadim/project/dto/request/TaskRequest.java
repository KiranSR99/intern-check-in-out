package com.aadim.project.dto.request;

import com.aadim.project.entity.Task;
import com.aadim.project.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequest {
    private List<TaskReq> tasks;
}
