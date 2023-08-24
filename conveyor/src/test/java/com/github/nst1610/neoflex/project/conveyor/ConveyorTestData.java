package com.github.nst1610.neoflex.project.conveyor;

import com.github.nst1610.neoflex.project.conveyor.dto.EmploymentDTO;
import com.github.nst1610.neoflex.project.conveyor.dto.LoanApplicationRequest;
import com.github.nst1610.neoflex.project.conveyor.dto.ScoringData;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ConveyorTestData {
    public static LoanApplicationRequest loanApplicationRequest;
    public static LoanApplicationRequest invalidLoanApplicationRequest;
    private static EmploymentDTO employment;
    public static ScoringData scoringData;
    public static ScoringData invalidScoringData;

    static {
        loanApplicationRequest = LoanApplicationRequest.builder()
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
        invalidLoanApplicationRequest = LoanApplicationRequest.builder()
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
        scoringData = ScoringData.builder()
                .amount(BigDecimal.valueOf(500000))
                .term(12)
                .firstName("Ivan")
                .lastName("Ivanovich")
                .middleName("Ivanov")
                .gender(ScoringData.GenderEnum.MALE)
                .birthDate(LocalDate.parse("1990-11-11"))
                .passportSeries("1111")
                .passportNumber("222222")
                .passportIssueDate(LocalDate.parse("2010-11-11"))
                .passportIssueBranch("Отеление УФМС России")
                .maritalStatus(ScoringData.MaritalStatusEnum.SINGLE)
                .dependentAmount(0)
                .employment(employment)
                .account("55555")
                .isInsuranceEnabled(true)
                .isSalaryClient(false)
                .build();
        invalidScoringData = ScoringData.builder()
                .amount(BigDecimal.valueOf(10000000))
                .term(12)
                .firstName("Ivan")
                .lastName("Ivanovich")
                .middleName("Ivanov")
                .gender(ScoringData.GenderEnum.MALE)
                .birthDate(LocalDate.parse("1990-11-11"))
                .passportSeries("1111")
                .passportNumber("222222")
                .passportIssueDate(LocalDate.parse("2010-11-11"))
                .passportIssueBranch("Отеление УФМС России")
                .maritalStatus(ScoringData.MaritalStatusEnum.SINGLE)
                .dependentAmount(0)
                .employment(employment)
                .account("55555")
                .isInsuranceEnabled(true)
                .isSalaryClient(false)
                .build();
    }
}
