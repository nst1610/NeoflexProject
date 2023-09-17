package com.github.nst1610.neoflex.project.gateway.api.dto;

import com.github.nst1610.neoflex.project.gateway.model.enums.EmploymentPosition;
import com.github.nst1610.neoflex.project.gateway.model.enums.EmploymentStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class EmploymentDTO {
    private EmploymentStatus employmentStatus;
    private String employerINN;
    private BigDecimal salary;
    private EmploymentPosition position;
    private Integer workExperienceTotal;
    private Integer workExperienceCurrent;
}
