package com.github.nst1610.neoflex.project.conveyor.service;

import com.github.nst1610.neoflex.project.conveyor.ConveyorTestData;
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

        Assertions.assertEquals(0, service.preScore(ConveyorTestData.loanApplicationRequest).size());
    }

    @Test
    void preScoreInvalidData() {

        Assertions.assertEquals(1, service.preScore(ConveyorTestData.invalidLoanApplicationRequest).size());
    }
}
