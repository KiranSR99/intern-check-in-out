package com.aadim.project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleStatusResponse {

    private boolean hasCheckedIn;
    private boolean hasCheckedOut;

}
