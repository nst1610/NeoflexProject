package com.github.nst1610.neoflex.project.gateway.service;

import com.github.nst1610.neoflex.project.gateway.api.dto.FinishRegistrationRequest;
import com.github.nst1610.neoflex.project.gateway.api.dto.LoanApplicationRequest;
import com.github.nst1610.neoflex.project.gateway.api.dto.LoanOffer;
import com.github.nst1610.neoflex.project.gateway.client.ApplicationMSClient;
import com.github.nst1610.neoflex.project.gateway.client.DealMSClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationService {
    private final ApplicationMSClient applicationMSClient;
    private final DealMSClient dealMSClient;
    public List<LoanOffer> createLoanOfferForClient(LoanApplicationRequest loanApplicationRequest) {
        log.info("Запрос на формирование кредитных предложений: {}", loanApplicationRequest);
        return applicationMSClient.calculatePossibleOffers(loanApplicationRequest).getBody();
    }

    public void chooseOffer(LoanOffer loanOffer) {
        log.info("Выбрано кредитное предложение: {}", loanOffer);
        applicationMSClient.chooseOffer(loanOffer);
    }

    public void finishRegistration(Long applicationId, FinishRegistrationRequest finishRegistrationRequest) {
        log.info("Запрос на завершение регистрации для заявки №{}. {}", applicationId, finishRegistrationRequest);
        dealMSClient.calculateCreditConditions(applicationId, finishRegistrationRequest);
    }
}
