package com.github.nst1610.neoflex.project.conveyor.calculator;

import com.github.nst1610.neoflex.project.conveyor.dto.EmploymentDTO;
import com.github.nst1610.neoflex.project.conveyor.dto.PaymentScheduleElement;
import com.github.nst1610.neoflex.project.conveyor.dto.ScoringData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

public class CreditCalculatorTest {
    private static CreditCalculator calculator;
    private static BigDecimal rate;
    private static BigDecimal requestedAmount;
    private static Integer term;
    private static BigDecimal totalAmount;
    private static BigDecimal monthlyPayment;

    @BeforeAll
    static void init() {
        calculator = new CreditCalculator();
        rate = BigDecimal.valueOf(10);
        requestedAmount = BigDecimal.valueOf(1000000);
        term = 12;
        totalAmount = BigDecimal.valueOf(1080000.00);
        monthlyPayment = BigDecimal.valueOf(87900.00);
    }

    @Test
    void calculateRateByInsuranceTest() {
        Assertions.assertEquals(0,
                calculator.calculateRateByInsurance(true, rate).compareTo(BigDecimal.valueOf(7)));
        Assertions.assertEquals(0,
                calculator.calculateRateByInsurance(false, rate).compareTo(rate));
    }

    @Test
    void calculateRateBySalaryClientTest() {
        Assertions.assertEquals(0,
                calculator.calculateRateBySalaryClient(true, rate).compareTo(BigDecimal.valueOf(8)));
        Assertions.assertEquals(0,
                calculator.calculateRateBySalaryClient(false, rate).compareTo(BigDecimal.valueOf(11)));
    }

    @Test
    void calculateRateByEmploymentStatusTest() {
        Assertions.assertEquals(0,
                calculator.calculateRateByEmploymentStatus(EmploymentDTO.EmploymentStatusEnum.SELF_EMPLOYED, rate)
                        .compareTo(BigDecimal.valueOf(11)));
        Assertions.assertEquals(0,
                calculator.calculateRateByEmploymentStatus(EmploymentDTO.EmploymentStatusEnum.BUSINESS_OWNER, rate)
                        .compareTo(BigDecimal.valueOf(13)));
    }

    @Test
    void calculateRateByPositionTest() {
        Assertions.assertEquals(0,
                calculator.calculateRateByPosition(EmploymentDTO.PositionEnum.MID_MANAGER, rate)
                        .compareTo(BigDecimal.valueOf(8)));
        Assertions.assertEquals(0,
                calculator.calculateRateByPosition(EmploymentDTO.PositionEnum.TOP_MANAGER, rate)
                        .compareTo(BigDecimal.valueOf(7)));
        Assertions.assertEquals(0,
                calculator.calculateRateByPosition(EmploymentDTO.PositionEnum.DIRECTOR, rate)
                        .compareTo(BigDecimal.valueOf(6)));
    }

    @Test
    void calculateRateByMaritalStatusTest() {
        Assertions.assertEquals(0,
                calculator.calculateRateByMaritalStatus(ScoringData.MaritalStatusEnum.MARRIED, rate)
                        .compareTo(BigDecimal.valueOf(8)));
        Assertions.assertEquals(0,
                calculator.calculateRateByMaritalStatus(ScoringData.MaritalStatusEnum.DIVORCED, rate)
                        .compareTo(BigDecimal.valueOf(11)));
    }

    @Test
    void calculateRateByDependentAmountTest() {
        Assertions.assertEquals(0,
                calculator.calculateRateByDependentAmount(3, rate)
                        .compareTo(BigDecimal.valueOf(11)));
        Assertions.assertEquals(0,
                calculator.calculateRateByDependentAmount(0, rate)
                        .compareTo(rate));
    }

    @Test
    void calculateRateByGenderTest() {
        Assertions.assertEquals(0,
                calculator.calculateRateByGender(ScoringData.GenderEnum.MALE, 40, rate)
                        .compareTo(BigDecimal.valueOf(7)));
        Assertions.assertEquals(0,
                calculator.calculateRateByGender(ScoringData.GenderEnum.MALE, 22, rate)
                        .compareTo(rate));
        Assertions.assertEquals(0,
                calculator.calculateRateByGender(ScoringData.GenderEnum.FEMALE, 40, rate)
                        .compareTo(BigDecimal.valueOf(7)));
        Assertions.assertEquals(0,
                calculator.calculateRateByGender(ScoringData.GenderEnum.FEMALE, 22, rate)
                        .compareTo(rate));
        Assertions.assertEquals(0,
                calculator.calculateRateByGender(ScoringData.GenderEnum.NOT_BINARY, 40, rate)
                        .compareTo(BigDecimal.valueOf(13)));
    }

    @Test
    void calculateTotalAmountTest() {
        Assertions.assertEquals(0, calculator.calculateTotalAmount(requestedAmount, true)
                .compareTo(BigDecimal.valueOf(1080000.00)));
        Assertions.assertEquals(0, calculator.calculateTotalAmount(requestedAmount, false)
                .compareTo(requestedAmount));
    }

    @Test
    void calculateMonthlyPaymentTest() {
        Assertions.assertEquals(0, calculator.calculateMonthlyPayment(requestedAmount, term, rate)
                .compareTo(BigDecimal.valueOf(87900.00)));
    }

    @Test
    void calculatePskTest() {
        Assertions.assertEquals(0, calculator.calculatePsk(totalAmount, requestedAmount, term)
                .compareTo(BigDecimal.valueOf(108.00)));
    }

    @Test
    void getPaymentScheduleTest() {
        List<PaymentScheduleElement> paymentSchedule = calculator
                .getPaymentSchedule(term, monthlyPayment, totalAmount, rate);
        Assertions.assertEquals(12, paymentSchedule.size());
    }
}
