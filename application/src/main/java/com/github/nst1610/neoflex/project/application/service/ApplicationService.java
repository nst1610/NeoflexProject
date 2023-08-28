package com.github.nst1610.neoflex.project.application.service;

import com.github.nst1610.neoflex.project.application.client.DealMSClient;
import com.github.nst1610.neoflex.project.application.api.dto.LoanApplicationRequest;
import com.github.nst1610.neoflex.project.application.api.dto.LoanOffer;
import com.github.nst1610.neoflex.project.application.api.exception.PrescoringException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationService {
    private final PreScoringService preScoringService;
    private final DealMSClient dealMSClient;

    public List<LoanOffer> createLoanOfferForClient(LoanApplicationRequest loanApplicationRequest) {
        log.info("Начало формирования кредитных предложений.");
        Map<String, String> preScoringResult = preScoringService.preScore(loanApplicationRequest);
        if (!preScoringResult.isEmpty()) {
            throw new PrescoringException(preScoringResult.toString());
        }
        log.info("Возможные редитные предложения сформированы.");
        return dealMSClient.calculatePossibleOffers(loanApplicationRequest).getBody();
    }

    public void chooseOffer(LoanOffer loanOffer) {
        dealMSClient.chooseOffer(loanOffer);
        log.info("Выбрано кредитное предложение.");
    }

}
