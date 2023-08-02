package com.github.nst1610.neoflex.project.conveyor.calculator;

import com.github.nst1610.neoflex.project.conveyor.constant.EmploymentStatus;
import com.github.nst1610.neoflex.project.conveyor.constant.Gender;
import com.github.nst1610.neoflex.project.conveyor.constant.MaritalStatus;
import com.github.nst1610.neoflex.project.conveyor.constant.Position;
import com.github.nst1610.neoflex.project.conveyor.model.PaymentScheduleElement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class CreditCalculator {
    @Value("${insurancePercentCost}")
    private BigDecimal INSURANCE_PERCENT_COST;
    private final BigDecimal MONTHS_IN_YEAR = BigDecimal.valueOf(12);

    public BigDecimal calculateRateByInsurance(Boolean isInsuranceEnabled, BigDecimal rate){
        return isInsuranceEnabled ? rate.subtract(BigDecimal.valueOf(3)) : rate;
    }

    public BigDecimal calculateRateBySalaryClient(Boolean isSalaryClient, BigDecimal rate){
        return isSalaryClient ? rate.subtract(BigDecimal.valueOf(2)) : rate.add(BigDecimal.valueOf(1));
    }

    public BigDecimal calculateRateByEmploymentStatus(EmploymentStatus status, BigDecimal rate){
        switch (status){
            case SELF_EMPLOYED:
                return rate.add(BigDecimal.valueOf(1));
            case BUSINESS_OWNER:
                return rate.add(BigDecimal.valueOf(3));
        }
        return rate;
    }

    public BigDecimal calculateRateByPosition(Position position, BigDecimal rate){
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

    public BigDecimal calculateRateByMaritalStatus(MaritalStatus status, BigDecimal rate){
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

    public BigDecimal calculateRateByGender(Gender gender, Integer age, BigDecimal rate){
        switch (gender){
            case MALE:
                if (age >= 30 && age <= 55) {
                    return rate.subtract(BigDecimal.valueOf(3));
                }
                break;
            case FEMALE:
                if (age >= 35 && age <= 60) {
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
                .multiply(INSURANCE_PERCENT_COST.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));

        log.info("Стоимость кредита с учетом страховки рассчитана.");

        return isInsuranceEnabled ? totalAmount.add(insuranceCost) : totalAmount;
    }

    public BigDecimal calculateMonthlyPayment(BigDecimal totalAmount, Integer term, BigDecimal rate){
        log.info("Расчет ежемесячного платежа.");

        BigDecimal monthlyRate = rate.divide(MONTHS_IN_YEAR, 2, RoundingMode.HALF_UP)
                .divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);
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
                .divide(countOfYears, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));

        log.info("Расчет ПСК в процентах завершен.");
        return psk;
    }

    public List<PaymentScheduleElement> getPaymentSchedule(Integer term, BigDecimal monthlyPayment,
                                                           BigDecimal totalAmount, BigDecimal rate){
        log.info("Начат расчет графика платежей.");

        List<PaymentScheduleElement> paymentScheduleElements = new ArrayList<>();

        LocalDate lastPaymentDate = LocalDate.now();
        int daysInYear = lastPaymentDate.lengthOfYear();
        BigDecimal currentRemainingDebt = totalAmount;

        for (int i = 1; i <= term; i++){
            lastPaymentDate = lastPaymentDate.plusMonths(1);
            BigDecimal interestPayment = currentRemainingDebt
                    .multiply(rate.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP))
                    .multiply(BigDecimal.valueOf(lastPaymentDate.lengthOfMonth()))
                    .divide(BigDecimal.valueOf(daysInYear), 2, RoundingMode.HALF_UP);
            BigDecimal debtPayment = monthlyPayment.setScale(2, RoundingMode.HALF_UP)
                    .subtract(interestPayment);
            currentRemainingDebt = currentRemainingDebt.compareTo(debtPayment) > 0
                    ? currentRemainingDebt.subtract(debtPayment) : BigDecimal.ZERO;

            paymentScheduleElements.add(
                    new PaymentScheduleElement(
                            i, lastPaymentDate, monthlyPayment,
                            interestPayment, debtPayment, currentRemainingDebt)
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
