package com.github.nst1610.neoflex.project.conveyor.service;

import com.github.nst1610.neoflex.project.conveyor.ConveyorTestData;
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

    @BeforeAll
    static void init() {
        preScoringService = new PreScoringService();
        calculator = new CreditCalculator();
        loanOfferService = new LoanOfferService(preScoringService, calculator);
        ReflectionTestUtils.setField(loanOfferService, "BASE_RATE", BigDecimal.valueOf(10));
    }

    @Test
    void createLoanOfferForClientTest() {
        List<LoanOfferDTO> response = loanOfferService.createLoanOfferForClient(ConveyorTestData.loanApplicationRequest);
        assertEquals(4, response.size());
    }
}
