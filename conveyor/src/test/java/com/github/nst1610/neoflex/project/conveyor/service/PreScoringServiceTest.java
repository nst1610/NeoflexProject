package com.github.nst1610.neoflex.project.conveyor.service;

import com.github.nst1610.neoflex.project.conveyor.dto.LoanApplicationRequestDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PreScoringServiceTest {
    private static PreScoringService service;

    @BeforeAll
    static void init() {
        service = new PreScoringService();
    }

    @Test
    void preScoreValidData() {
        LoanApplicationRequestDTO validData = LoanApplicationRequestDTO.builder()
                .amount(BigDecimal.valueOf(500000))
                .term(12)
                .firstName("Ivan")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .email("ivan@mail.ru")
                .birthDate(LocalDate.parse("1999-11-11"))
                .passportSeries("1111")
                .passportNumber("222222")
                .build();

        Assertions.assertEquals(0, service.preScore(validData).size());
    }

    @Test
    void preScoreInvalidData() {
        LoanApplicationRequestDTO invalidData = LoanApplicationRequestDTO.builder()
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

        Assertions.assertEquals(1, service.preScore(invalidData).size());
    }
}
