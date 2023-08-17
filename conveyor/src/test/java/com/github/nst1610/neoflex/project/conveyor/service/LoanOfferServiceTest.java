package com.github.nst1610.neoflex.project.conveyor.service;

import com.github.nst1610.neoflex.project.conveyor.calculator.CreditCalculator;
import com.github.nst1610.neoflex.project.conveyor.dto.LoanApplicationRequestDTO;
import com.github.nst1610.neoflex.project.conveyor.dto.LoanOfferDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoanOfferServiceTest {
    private static PreScoringService preScoringService;
    private static CreditCalculator calculator;
    private static LoanOfferService loanOfferService;
    private static LoanApplicationRequestDTO loanApplicationRequestDTO;

    @BeforeAll
    static void init() {
        preScoringService = new PreScoringService();
        calculator = new CreditCalculator();
        loanOfferService = new LoanOfferService(preScoringService, calculator);
        ReflectionTestUtils.setField(loanOfferService, "BASE_RATE", BigDecimal.valueOf(10));
        loanApplicationRequestDTO = LoanApplicationRequestDTO.builder()
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
    }

    @Test
    void createLoanOfferForClientTest() {
        List<LoanOfferDTO> response = loanOfferService.createLoanOfferForClient(loanApplicationRequestDTO);
        assertEquals(4, response.size());
    }
}
