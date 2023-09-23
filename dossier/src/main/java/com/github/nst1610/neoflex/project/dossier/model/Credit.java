package com.github.nst1610.neoflex.project.dossier.model;

import com.github.nst1610.neoflex.project.dossier.model.enums.CreditStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Credit {
    private Long creditId;
    private BigDecimal amount;
    private Integer term;
    private BigDecimal monthlyPayment;
    private BigDecimal rate;
    private BigDecimal psk;
    private List<PaymentScheduleElement> paymentSchedule;
    private Boolean isInsuranceEnabled;
    private Boolean isSalaryClient;
    private CreditStatus creditStatus;
}
