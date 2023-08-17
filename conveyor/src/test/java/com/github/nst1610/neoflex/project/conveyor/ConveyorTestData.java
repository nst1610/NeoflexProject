package com.github.nst1610.neoflex.project.conveyor;

import com.github.nst1610.neoflex.project.conveyor.dto.EmploymentDTO;
import com.github.nst1610.neoflex.project.conveyor.dto.LoanApplicationRequestDTO;
import com.github.nst1610.neoflex.project.conveyor.dto.ScoringDataDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ConveyorTestData {
    public static LoanApplicationRequestDTO loanApplicationRequest;
    public static LoanApplicationRequestDTO invalidLoanApplicationRequest;
    private static EmploymentDTO employment;
    public static ScoringDataDTO scoringData;
    public static ScoringDataDTO invalidScoringData;

    static {
        loanApplicationRequest = LoanApplicationRequestDTO.builder()
                .amount(BigDecimal.valueOf(500000))
                .term(12)
                .firstName("Ivan")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .email("ivan@mail.ru")
                .birthDate(LocalDate.parse("1911-11-11"))
                .passportSeries("1111")
                .passportNumber("222222")
                .build();
        invalidLoanApplicationRequest = LoanApplicationRequestDTO.builder()
                .amount(BigDecimal.valueOf(100)) // incorrect field
                .term(12)
                .firstName("Ivan")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .email("ivan@mail.ru")
                .birthDate(LocalDate.parse("1999-11-11"))
                .passportSeries("1111")
                .passportNumber("222222")
                .build();
        employment = EmploymentDTO.builder()
                .employmentStatus(EmploymentDTO.EmploymentStatusEnum.SELF_EMPLOYED)
                .employerINN("1111111")
                .salary(BigDecimal.valueOf(50000))
                .position(EmploymentDTO.PositionEnum.WORKER)
                .workExperienceTotal(36)
                .workExperienceCurrent(6)
                .build();
        scoringData = ScoringDataDTO.builder()
                .amount(BigDecimal.valueOf(500000))
                .term(12)
                .firstName("Ivan")
                .lastName("Ivanovich")
                .middleName("Ivanov")
                .gender(ScoringDataDTO.GenderEnum.MALE)
                .birthDate(LocalDate.parse("1990-11-11"))
                .passportSeries("1111")
                .passportNumber("222222")
                .passportIssueDate(LocalDate.parse("2010-11-11"))
                .passportIssueBranch("Отеление УФМС России")
                .maritalStatus(ScoringDataDTO.MaritalStatusEnum.SINGLE)
                .dependentAmount(0)
                .employment(employment)
                .account("55555")
                .isInsuranceEnabled(true)
                .isSalaryClient(false)
                .build();
        invalidScoringData = ScoringDataDTO.builder()
                .amount(BigDecimal.valueOf(10000000))
                .term(12)
                .firstName("Ivan")
                .lastName("Ivanovich")
                .middleName("Ivanov")
                .gender(ScoringDataDTO.GenderEnum.MALE)
                .birthDate(LocalDate.parse("1990-11-11"))
                .passportSeries("1111")
                .passportNumber("222222")
                .passportIssueDate(LocalDate.parse("2010-11-11"))
                .passportIssueBranch("Отеление УФМС России")
                .maritalStatus(ScoringDataDTO.MaritalStatusEnum.SINGLE)
                .dependentAmount(0)
                .employment(employment)
                .account("55555")
                .isInsuranceEnabled(true)
                .isSalaryClient(false)
                .build();
    }
}
