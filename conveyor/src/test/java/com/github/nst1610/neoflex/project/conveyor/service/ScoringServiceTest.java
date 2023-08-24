package com.github.nst1610.neoflex.project.conveyor.service;

import com.github.nst1610.neoflex.project.conveyor.ConveyorTestData;
import com.github.nst1610.neoflex.project.conveyor.exception.ScoringException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ScoringServiceTest {
    private static ScoringService service;

    @BeforeAll
    static void init() {
        service = new ScoringService();
    }

    @Test
    void scoringValidData() {
        Assertions.assertDoesNotThrow(() -> service.validateScoringData(ConveyorTestData.scoringData));
    }

    @Test
    void scoringInvalidData() {
        Assertions.assertThrows(ScoringException.class,
                () -> service.validateScoringData(ConveyorTestData.invalidScoringData));
    }
}
