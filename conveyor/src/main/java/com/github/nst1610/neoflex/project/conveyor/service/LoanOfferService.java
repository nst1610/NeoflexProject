package com.github.nst1610.neoflex.project.conveyor.service;

import com.github.nst1610.neoflex.project.conveyor.calculator.CreditCalculator;
import com.github.nst1610.neoflex.project.conveyor.dto.LoanApplicationRequest;
import com.github.nst1610.neoflex.project.conveyor.dto.LoanOffer;
import com.github.nst1610.neoflex.project.conveyor.exception.DataException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoanOfferService {
    @Value("${rate.base}")
    private BigDecimal BASE_RATE;

    private final PreScoringService preScoringService;
    private final CreditCalculator calculator;

    public List<LoanOffer> createLoanOfferForClient(LoanApplicationRequest loanApplicationRequest){
        Map<String, String> preScoringResult = preScoringService.preScore(loanApplicationRequest);
        if (!preScoringResult.isEmpty()) {
            throw new DataException(preScoringResult.toString());
        }

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

        BigDecimal requestedAmount = loanApplicationRequest.getAmount();
        BigDecimal totalAmount = calculator.calculateTotalAmount(requestedAmount, isInsuranceEnabled);
        Integer term = loanApplicationRequest.getTerm();
        BigDecimal rate = calculateRate(isInsuranceEnabled, isSalaryClient);
        BigDecimal monthlyPayment = calculator.calculateMonthlyPayment(totalAmount, term, rate);

        log.info("Конец формирования кпредитного предложения с параметрами: наличие страховки - " +
                isInsuranceEnabled + ", зарплатный клиент - " + isSalaryClient + "\n");
        return LoanOffer.builder()
                .applicationId(applicationId)
                .requestedAmount(requestedAmount)
                .totalAmount(totalAmount)
                .term(term)
                .rate(rate)
                .monthlyPayment(monthlyPayment)
                .isInsuranceEnabled(isInsuranceEnabled)
                .isSalaryClient(isSalaryClient)
                .build();
    }

    private BigDecimal calculateRate(Boolean isInsuranceEnabled, Boolean isSalaryClient){
        log.info("Начало расчета процентной ставки.");

        BigDecimal rate = calculator.calculateRateByInsurance(isInsuranceEnabled, BASE_RATE);
        rate = calculator.calculateRateBySalaryClient(isSalaryClient, rate);

        log.info("Процентная ставка рассчитана.");
        return rate;
    }
}
