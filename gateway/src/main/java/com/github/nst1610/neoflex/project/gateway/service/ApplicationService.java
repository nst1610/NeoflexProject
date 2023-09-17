package com.github.nst1610.neoflex.project.gateway.service;

import com.github.nst1610.neoflex.project.gateway.api.dto.FinishRegistrationRequest;
import com.github.nst1610.neoflex.project.gateway.api.dto.LoanApplicationRequest;
import com.github.nst1610.neoflex.project.gateway.api.dto.LoanOffer;
import com.github.nst1610.neoflex.project.gateway.client.ApplicationMSClient;
import com.github.nst1610.neoflex.project.gateway.client.DealMSClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationMSClient applicationMSClient;
    private final DealMSClient dealMSClient;
    public List<LoanOffer> createLoanOfferForClient(LoanApplicationRequest loanApplicationRequest) {
        return applicationMSClient.calculatePossibleOffers(loanApplicationRequest).getBody();
    }

    public void chooseOffer(LoanOffer loanOffer) {
        applicationMSClient.chooseOffer(loanOffer);
    }

    public void finishRegistration(Long applicationId, FinishRegistrationRequest finishRegistrationRequest) {
        dealMSClient.calculateCreditConditions(applicationId, finishRegistrationRequest);
    }
}
