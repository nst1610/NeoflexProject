package com.github.nst1610.neoflex.project.deal.dto;

import com.github.nst1610.neoflex.project.deal.model.enums.Gender;
import com.github.nst1610.neoflex.project.deal.model.enums.MaritalStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class FinishRegistrationRequestDTO {
    private Gender gender;
    private MaritalStatus maritalStatus;
    private Integer dependentAmount;
    private LocalDate passportIssueDate;
    private String passportIssueBranch;
    private EmploymentDTO employment;
    private String account;
}
