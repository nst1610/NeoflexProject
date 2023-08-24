package com.github.nst1610.neoflex.project.conveyor.service;

import com.github.nst1610.neoflex.project.conveyor.calculator.CreditCalculator;
import com.github.nst1610.neoflex.project.conveyor.dto.CreditDTO;
import com.github.nst1610.neoflex.project.conveyor.dto.PaymentScheduleElement;
import com.github.nst1610.neoflex.project.conveyor.dto.ScoringData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreditService {
    @Value("${rate.base}")
    private BigDecimal BASE_RATE;
    private final ScoringService scoringService;
    private final CreditCalculator calculator;

    public CreditDTO getCalculatedCreditOffer(ScoringData scoringData){
        log.info("Начало расчета параметров кредита.\n");

        scoringService.validateScoringData(scoringData);

        BigDecimal amount = scoringData.getAmount();
        Integer term = scoringData.getTerm();
        BigDecimal rate = calculateRate(scoringData);
        Boolean isInsuranceEnabled = scoringData.getIsInsuranceEnabled();
        Boolean isSalaryClient = scoringData.getIsSalaryClient();
        BigDecimal monthlyPayment = calculator.calculateMonthlyPayment(calculator.calculateTotalAmount(amount,
                isInsuranceEnabled), term, rate);
        List<PaymentScheduleElement> paymentSchedule = calculator.getPaymentSchedule(term, monthlyPayment,
                calculator.calculateTotalAmount(amount, isInsuranceEnabled), rate);
        BigDecimal psk = calculator.calculatePsk(
                calculator.calculateTotalAmountWithPercent(amount, isInsuranceEnabled,
                        paymentSchedule), amount, term);

        log.info("Параметры кредита рассчитаны.\n");

        return CreditDTO.builder()
                .amount(amount)
                .term(term)
                .rate(rate)
                .monthlyPayment(monthlyPayment)
                .isInsuranceEnabled(isInsuranceEnabled)
                .isSalaryClient(isSalaryClient)
                .paymentSchedule(paymentSchedule)
                .psk(psk).build();
    }

    private BigDecimal calculateRate(ScoringData scoringData){
        log.info("Начало расчета процентной ставки.\n");

        BigDecimal rate = calculator.calculateRateByInsurance(scoringData.getIsInsuranceEnabled(), BASE_RATE);
        rate = calculator.calculateRateBySalaryClient(scoringData.getIsSalaryClient(), rate);
        rate = calculator.calculateRateByEmploymentStatus(scoringData.getEmployment().getEmploymentStatus(), rate);
        rate = calculator.calculateRateByPosition(scoringData.getEmployment().getPosition(), rate);
        rate = calculator.calculateRateByMaritalStatus(scoringData.getMaritalStatus(), rate);
        rate = calculator.calculateRateByDependentAmount(scoringData.getDependentAmount(), rate);
        rate = calculator.calculateRateByGender(scoringData.getGender(), calculateAge(scoringData.getBirthDate()), rate);

        log.info("Процентная ставка рассчитана.\n");
        return rate;
    }

    private Integer calculateAge(LocalDate birthDate){
        return Period.between(birthDate, LocalDate.now()).getYears();
    }


}
