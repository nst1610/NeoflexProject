package com.github.nst1610.neoflex.project.conveyor.calculator;

import com.github.nst1610.neoflex.project.conveyor.dto.EmploymentDTO;
import com.github.nst1610.neoflex.project.conveyor.dto.PaymentScheduleElement;
import com.github.nst1610.neoflex.project.conveyor.dto.ScoringData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.github.nst1610.neoflex.project.conveyor.util.Constant.*;

@Component
@Slf4j
public class CreditCalculator {

    public BigDecimal calculateRateByInsurance(Boolean isInsuranceEnabled, BigDecimal rate){
        return isInsuranceEnabled ? rate.subtract(BigDecimal.valueOf(3)) : rate;
    }

    public BigDecimal calculateRateBySalaryClient(Boolean isSalaryClient, BigDecimal rate){
        return isSalaryClient ? rate.subtract(BigDecimal.valueOf(2)) : rate.add(BigDecimal.valueOf(1));
    }

    public BigDecimal calculateRateByEmploymentStatus(EmploymentDTO.EmploymentStatusEnum status, BigDecimal rate){
        switch (status){
            case SELF_EMPLOYED:
                return rate.add(BigDecimal.valueOf(1));
            case BUSINESS_OWNER:
                return rate.add(BigDecimal.valueOf(3));
        }
        return rate;
    }

    public BigDecimal calculateRateByPosition(EmploymentDTO.PositionEnum position, BigDecimal rate){
        switch (position){
            case MID_MANAGER:
                return rate.subtract(BigDecimal.valueOf(2));
            case TOP_MANAGER:
                return rate.subtract(BigDecimal.valueOf(3));
            case DIRECTOR:
                return rate.subtract(BigDecimal.valueOf(4));
        }
        return rate;
    }

    public BigDecimal calculateRateByMaritalStatus(ScoringData.MaritalStatusEnum status, BigDecimal rate){
        switch (status){
            case MARRIED:
                return rate.subtract(BigDecimal.valueOf(2));
            case DIVORCED:
                return rate.add(BigDecimal.valueOf(1));
        }
        return rate;
    }

    public BigDecimal calculateRateByDependentAmount(Integer dependentAmount, BigDecimal rate){
        return dependentAmount > 1 ? rate.add(BigDecimal.valueOf(1)) : rate;
    }

    public BigDecimal calculateRateByGender(ScoringData.GenderEnum gender, Integer age, BigDecimal rate){
        switch (gender){
            case MALE:
                if (age >= MIN_AGE_FOR_MALE && age <= MAX_AGE_FOR_MALE) {
                    return rate.subtract(BigDecimal.valueOf(3));
                }
                break;
            case FEMALE:
                if (age >= MIN_AGE_FOR_FEMALE && age <= MAX_AGE_FOR_FEMALE) {
                    return rate.subtract(BigDecimal.valueOf(3));
                }
                break;
            case NOT_BINARY:
                return rate.add(BigDecimal.valueOf(3));
        }
        return rate;
    }

    public BigDecimal calculateTotalAmount(BigDecimal requestedAmount, Boolean isInsuranceEnabled){
        log.info("Начало расчета стоимости кредита с учетом страховки.");

        BigDecimal totalAmount = requestedAmount.setScale(2, RoundingMode.HALF_UP);
        BigDecimal insuranceCost = requestedAmount
                .multiply(INSURANCE_PERCENT_COST.divide(ONE_HUNDRED, 2, RoundingMode.HALF_UP));

        log.info("Стоимость кредита с учетом страховки рассчитана.");

        return isInsuranceEnabled ? totalAmount.add(insuranceCost) : totalAmount;
    }

    public BigDecimal calculateMonthlyPayment(BigDecimal totalAmount, Integer term, BigDecimal rate){
        log.info("Расчет ежемесячного платежа.");

        BigDecimal monthlyRate = rate.divide(MONTHS_IN_YEAR, 2, RoundingMode.HALF_UP)
                .divide(ONE_HUNDRED, 4, RoundingMode.HALF_UP);
        BigDecimal pow = BigDecimal.valueOf(1).setScale(4,RoundingMode.HALF_UP).add(monthlyRate).pow(term);
        BigDecimal annuityRate = monthlyRate.multiply(pow)
                .divide(pow.subtract(BigDecimal.valueOf(1)), 4, RoundingMode.HALF_UP);

        log.info("Ежемесячный платеж рассчитан.");
        return totalAmount.multiply(annuityRate).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculatePsk(BigDecimal totalAmount, BigDecimal requestedAmount, Integer term){
        log.info("Расчет ПСК в процентах.");

        BigDecimal countOfYears = BigDecimal.valueOf(term).divide(MONTHS_IN_YEAR, 4, RoundingMode.HALF_UP);
        BigDecimal psk = totalAmount.divide(requestedAmount, 4, RoundingMode.HALF_UP)
                .divide(countOfYears, 4, RoundingMode.HALF_UP).multiply(ONE_HUNDRED)
                .setScale(2, RoundingMode.HALF_UP);

        log.info("Расчет ПСК в процентах завершен.");
        return psk;
    }

    public List<PaymentScheduleElement> getPaymentSchedule(Integer term, BigDecimal monthlyPayment,
                                                           BigDecimal totalAmount, BigDecimal rate){
        log.info("Начат расчет графика платежей.");

        List<PaymentScheduleElement> paymentScheduleElements = new ArrayList<>();

        LocalDate lastPaymentDate = LocalDate.now();
        int daysInYear = lastPaymentDate.lengthOfYear();
        BigDecimal currentRemainingDebt = totalAmount.setScale(2, RoundingMode.HALF_UP);

        for (int i = 1; i <= term; i++){
            lastPaymentDate = lastPaymentDate.plusMonths(1);
            BigDecimal interestPayment = currentRemainingDebt
                    .multiply(rate.divide(ONE_HUNDRED, 2, RoundingMode.HALF_UP))
                    .multiply(BigDecimal.valueOf(lastPaymentDate.lengthOfMonth()))
                    .divide(BigDecimal.valueOf(daysInYear), 2, RoundingMode.HALF_UP);
            BigDecimal debtPayment = monthlyPayment.setScale(2, RoundingMode.HALF_UP)
                    .subtract(interestPayment);
            currentRemainingDebt = currentRemainingDebt.compareTo(debtPayment) > 0
                    ? currentRemainingDebt.subtract(debtPayment) : BigDecimal.ZERO;

            paymentScheduleElements.add(
                    PaymentScheduleElement.builder()
                            .number(i)
                            .date(lastPaymentDate)
                            .totalPayment(monthlyPayment)
                            .interestPayment(interestPayment)
                            .debtPayment(debtPayment)
                            .remainingDebt(currentRemainingDebt)
                            .build()
            );
        }

        log.info("Закончен расчет графика платежей.");
        return paymentScheduleElements;
    }

    public BigDecimal calculateTotalAmountWithPercent(BigDecimal requestedAmount, Boolean isInsuranceEnabled,
                                                      List<PaymentScheduleElement> paymentScheduleElements){
        return calculateTotalAmount(requestedAmount, isInsuranceEnabled).setScale(2, RoundingMode.HALF_UP)
                .add(paymentScheduleElements.stream()
                        .map(PaymentScheduleElement::getInterestPayment)
                        .reduce(BigDecimal::add).get());
    }
}
