package com.github.nst1610.neoflex.project.deal.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class LoanApplicationRequestDTO {
    private BigDecimal amount;

    private Integer term;

    private String firstName;

    private String lastName;

    private String middleName;

    private String email;

    private LocalDate birthDate;

    private String passportSeries;

    private String passportNumber;
}
