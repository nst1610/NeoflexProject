package com.github.nst1610.neoflex.project.conveyor.service;

import com.github.nst1610.neoflex.project.conveyor.calculator.CreditCalculator;
import com.github.nst1610.neoflex.project.conveyor.model.LoanApplicationRequest;
import com.github.nst1610.neoflex.project.conveyor.model.LoanOffer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoanOfferService {
    @Value("${baseRate}")
    private BigDecimal BASE_RATE;

    private final CreditCalculator calculator;

    public List<LoanOffer> createLoanOfferForClient(LoanApplicationRequest loanApplicationRequest){
        log.info("Начало формирования возможных кредитных предложений для клиента.\n");

        List<LoanOffer> offers = new ArrayList<>();

        offers.add(createOffer(1L, loanApplicationRequest, false, false));
        offers.add(createOffer(2L, loanApplicationRequest, false, true));
        offers.add(createOffer(3L, loanApplicationRequest, true, false));
        offers.add(createOffer(4L, loanApplicationRequest, true, true));

        log.info("Возможные кредитные предложения сформированы.\n");
        return offers.stream()
                .sorted(Comparator.comparing(LoanOffer::getRate).reversed())
                .collect(Collectors.toList());
    }

    public LoanOffer createOffer(Long applicationId, LoanApplicationRequest loanApplicationRequest,
                                 Boolean isInsuranceEnabled, Boolean isSalaryClient){
        log.info("Начало формирования кпредитного предложения с параметрами: наличие страховки - " +
                isInsuranceEnabled + ", зарплатный клиент - " + isSalaryClient + "\n");
        LoanOffer loanOffer = new LoanOffer();

        loanOffer.setApplicationId(applicationId);
        loanOffer.setRequestedAmount(loanApplicationRequest.getAmount());
        loanOffer.setTotalAmount(calculator.calculateTotalAmount(loanApplicationRequest.getAmount(), isInsuranceEnabled));
        loanOffer.setTerm(loanApplicationRequest.getTerm());
        loanOffer.setRate(calculateRate(isInsuranceEnabled, isSalaryClient));
        loanOffer.setMonthlyPayment(calculator.calculateMonthlyPayment(loanOffer.getTotalAmount(),
                loanOffer.getTerm(), loanOffer.getRate()));
        loanOffer.setIsInsuranceEnabled(isInsuranceEnabled);
        loanOffer.setIsSalaryClient(isSalaryClient);

        log.info("Конец формирования кпредитного предложения с параметрами: наличие страховки - " +
                isInsuranceEnabled + ", зарплатный клиент - " + isSalaryClient + "\n");
        return loanOffer;
    }

    private BigDecimal calculateRate(Boolean isInsuranceEnabled, Boolean isSalaryClient){
        log.info("Начало расчета процентной ставки.");

        BigDecimal rate = calculator.calculateRateByInsurance(isInsuranceEnabled, BASE_RATE);
        rate = calculator.calculateRateBySalaryClient(isSalaryClient, rate);

        log.info("Процентная ставка рассчитана.");
        return rate;
    }
}
