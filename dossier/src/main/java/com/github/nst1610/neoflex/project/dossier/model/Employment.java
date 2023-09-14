package com.github.nst1610.neoflex.project.dossier.model;

import com.github.nst1610.neoflex.project.dossier.model.enums.EmploymentPosition;
import com.github.nst1610.neoflex.project.dossier.model.enums.EmploymentStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Employment {
    private EmploymentStatus employmentStatus;
    private String employerINN;
    private BigDecimal salary;
    private EmploymentPosition position;
    private Integer workExperienceTotal;
    private Integer workExperienceCurrent;
}
