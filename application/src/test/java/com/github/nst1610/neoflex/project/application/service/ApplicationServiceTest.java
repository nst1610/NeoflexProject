package com.github.nst1610.neoflex.project.application.service;

import com.github.nst1610.neoflex.project.application.ApplicationTestData;
import com.github.nst1610.neoflex.project.application.api.dto.LoanOffer;
import com.github.nst1610.neoflex.project.application.api.exception.PrescoringException;
import com.github.nst1610.neoflex.project.application.client.DealMSClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;

import static com.github.nst1610.neoflex.project.application.util.Message.INCORRECT_AMOUNT_MESSAGE;

@ExtendWith(MockitoExtension.class)
public class ApplicationServiceTest {
    @Mock
    private PreScoringService preScoringService;
    @Mock
    DealMSClient dealMSClient;
    @InjectMocks
    private ApplicationService applicationService;

    @Test
    void createLoanOfferForValidClientTest() {
        Mockito.when(preScoringService.preScore(ApplicationTestData.loanApplicationRequest))
                .thenReturn(new HashMap<>(0));
        Mockito.when(dealMSClient.calculatePossibleOffers(ApplicationTestData.loanApplicationRequest))
                .thenReturn(new ResponseEntity<>(ApplicationTestData.loanOfferList, HttpStatus.OK));
        List<LoanOffer> responseList = applicationService
                .createLoanOfferForClient(ApplicationTestData.loanApplicationRequest);

        Assertions.assertEquals(ApplicationTestData.loanOfferList, responseList);
    }

    @Test
    void createLoanOfferForInValidClientTest() {
        HashMap<String, String> incorrectField = new HashMap<>();
        incorrectField.put("amount", INCORRECT_AMOUNT_MESSAGE);
        Mockito.when(preScoringService.preScore(ApplicationTestData.invalidLoanApplicationRequest))
                .thenReturn(incorrectField);
        Assertions.assertThrows(PrescoringException.class,
                () -> applicationService.createLoanOfferForClient(ApplicationTestData.invalidLoanApplicationRequest));
    }

    @Test
    void chooseOfferTest() {
        applicationService.chooseOffer(ApplicationTestData.loanOfferList.get(0));
        Mockito.verify(dealMSClient, Mockito.times(1))
                .chooseOffer(ApplicationTestData.loanOfferList.get(0));
    }
}
