package com.github.nst1610.neoflex.project.conveyor.service;

import com.github.nst1610.neoflex.project.conveyor.dto.EmploymentDTO;
import com.github.nst1610.neoflex.project.conveyor.dto.ScoringDataDTO;
import com.github.nst1610.neoflex.project.conveyor.exception.ScoringException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ScoringServiceTest {
    private static ScoringService service;
    private static EmploymentDTO employmentDTO;

    @BeforeAll
    static void init() {
        service = new ScoringService();
        employmentDTO = EmploymentDTO.builder()
                .employmentStatus(EmploymentDTO.EmploymentStatusEnum.SELF_EMPLOYED)
                .employerINN("1111111")
                .salary(BigDecimal.valueOf(50000))
                .position(EmploymentDTO.PositionEnum.WORKER)
                .workExperienceTotal(36)
                .workExperienceCurrent(6)
                .build();
    }

    @Test
    void scoringValidData() {
        ScoringDataDTO validData = ScoringDataDTO.builder()
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
                .employment(employmentDTO)
                .account("55555")
                .isInsuranceEnabled(true)
                .isSalaryClient(false)
                .build();
        Assertions.assertDoesNotThrow(() -> service.validateScoringData(validData));
    }

    @Test
    void scoringInvalidData() {
        ScoringDataDTO invalidData = ScoringDataDTO.builder()
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
                .employment(employmentDTO)
                .account("55555")
                .isInsuranceEnabled(true)
                .isSalaryClient(false)
                .build();
        Assertions.assertThrows(ScoringException.class, () -> service.validateScoringData(invalidData));
    }
}
