package com.github.nst1610.neoflex.project.conveyor.service;

import com.github.nst1610.neoflex.project.conveyor.ConveyorTestData;
import com.github.nst1610.neoflex.project.conveyor.calculator.CreditCalculator;
import com.github.nst1610.neoflex.project.conveyor.dto.LoanOffer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        List<LoanOffer> response = loanOfferService.createLoanOfferForClient(ConveyorTestData.loanApplicationRequest);
        assertEquals(4, response.size());
    }
}
