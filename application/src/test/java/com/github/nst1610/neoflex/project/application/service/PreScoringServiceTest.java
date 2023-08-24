package com.github.nst1610.neoflex.project.application.service;

import com.github.nst1610.neoflex.project.application.ApplicationTestData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class PreScoringServiceTest {
    private static PreScoringService service;

    @BeforeAll
    static void init() {
        service = new PreScoringService();
    }

    @Test
    void preScoreValidData() {
        Assertions.assertEquals(0, service.preScore(ApplicationTestData.loanApplicationRequest).size());
    }

    @Test
    void preScoreInvalidData() {
        Assertions.assertEquals(1, service.preScore(ApplicationTestData.invalidLoanApplicationRequest).size());
    }
}
