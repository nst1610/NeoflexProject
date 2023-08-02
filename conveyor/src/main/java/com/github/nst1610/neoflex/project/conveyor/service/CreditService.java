package com.github.nst1610.neoflex.project.conveyor.service;

import com.github.nst1610.neoflex.project.conveyor.calculator.CreditCalculator;
import com.github.nst1610.neoflex.project.conveyor.model.Credit;
import com.github.nst1610.neoflex.project.conveyor.model.ScoringData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreditService {
    @Value("${baseRate}")
    private BigDecimal BASE_RATE;
    private final ScoringService scoringService;
    private final CreditCalculator calculator;

    public Credit getCalculatedCreditOffer(ScoringData scoringData){
        log.info("Начало расчета параметров кредита.\n");

        scoringService.validateScoringData(scoringData);

        Credit credit = new Credit();
        credit.setAmount(scoringData.getAmount());
        credit.setTerm(scoringData.getTerm());
        credit.setRate(calculateRate(scoringData));
        credit.setMonthlyPayment(
                calculator.calculateMonthlyPayment(calculator.calculateTotalAmount(scoringData.getAmount(),
                                scoringData.getIsInsuranceEnabled()), scoringData.getTerm(), credit.getRate()));
        credit.setIsInsuranceEnabled(scoringData.getIsInsuranceEnabled());
        credit.setIsSalaryClient(scoringData.getIsSalaryClient());
        credit.setPaymentSchedule(calculator.getPaymentSchedule(credit.getTerm(), credit.getMonthlyPayment(),
                calculator.calculateTotalAmount(scoringData.getAmount(), credit.getIsInsuranceEnabled()),
                credit.getRate()));
        credit.setPsk(calculator.calculatePsk(
                calculator.calculateTotalAmountWithPercent(scoringData.getAmount(), credit.getIsInsuranceEnabled(),
                        credit.getPaymentSchedule()),
                scoringData.getAmount(), credit.getTerm()));

        log.info("Параметры кредита рассчитаны.\n");

        return credit;
    }

    private BigDecimal calculateRate(ScoringData scoringData){
        log.info("Начало расчета процентной ставки.\n");

        BigDecimal rate = calculator.calculateRateByInsurance(scoringData.getIsInsuranceEnabled(), BASE_RATE);
        rate = calculator.calculateRateBySalaryClient(scoringData.getIsSalaryClient(), rate);
        rate = calculator.calculateRateByEmploymentStatus(scoringData.getEmployment().getEmploymentStatus(), rate);
        rate = calculator.calculateRateByPosition(scoringData.getEmployment().getPosition(), rate);
        rate = calculator.calculateRateByMaritalStatus(scoringData.getMaritalStatus(), rate);
        rate = calculator.calculateRateByDependentAmount(scoringData.getDependentAmount(), rate);
        rate = calculator.calculateRateByGender(scoringData.getGender(), calculateAge(scoringData.getBirthdate()), rate);

        log.info("Процентная ставка рассчитана.\n");
        return rate;
    }

    private Integer calculateAge(LocalDate birthDate){
        return Period.between(LocalDate.now(), birthDate).getYears();
    }


}
