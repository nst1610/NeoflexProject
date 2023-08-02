package com.github.nst1610.neoflex.project.conveyor.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class Credit {
    private BigDecimal amount;
    private Integer term;
    private BigDecimal monthlyPayment;
    private BigDecimal rate;
    private BigDecimal psk;
    private Boolean isInsuranceEnabled;
    private Boolean isSalaryClient;
    private List<PaymentScheduleElement> paymentSchedule;
}
