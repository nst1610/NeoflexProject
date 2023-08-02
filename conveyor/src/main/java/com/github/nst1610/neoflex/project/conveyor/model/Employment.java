package com.github.nst1610.neoflex.project.conveyor.model;

import com.github.nst1610.neoflex.project.conveyor.constant.EmploymentStatus;
import com.github.nst1610.neoflex.project.conveyor.constant.Position;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Employment {
    private EmploymentStatus employmentStatus;
    private String employerINN;
    private BigDecimal salary;
    private Position position;
    private Integer workExperienceTotal;
    private Integer workExperienceCurrent;
}
