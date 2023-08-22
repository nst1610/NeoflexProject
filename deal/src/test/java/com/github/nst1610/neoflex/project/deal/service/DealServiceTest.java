package com.github.nst1610.neoflex.project.deal.service;

import com.github.nst1610.neoflex.project.deal.DealTestData;
import com.github.nst1610.neoflex.project.deal.api.dto.FinishRegistrationRequest;
import com.github.nst1610.neoflex.project.deal.api.dto.LoanApplicationRequest;
import com.github.nst1610.neoflex.project.deal.api.dto.LoanOffer;
import com.github.nst1610.neoflex.project.deal.api.dto.ScoringDataDTO;
import com.github.nst1610.neoflex.project.deal.client.ConveyorMSClient;
import com.github.nst1610.neoflex.project.deal.repository.entity.Client;
import com.github.nst1610.neoflex.project.deal.service.mapper.CreditMapper;
import com.github.nst1610.neoflex.project.deal.service.mapper.EmploymentMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DealServiceTest {
    @Mock
    private ClientService clientService;
    @Mock
    private ApplicationService applicationService;
    @Mock
    private CreditService creditService;
    @Mock
    private ConveyorMSClient conveyorMSClient;
    @Mock
    private EmploymentMapper employmentMapper;
    @Mock
    private CreditMapper creditMapper;
    @InjectMocks
    private DealService dealService;

    @Test
    void getPossibleLoanOffersTest() {
        Mockito.when(conveyorMSClient.possibleOffersForClient(Mockito.any(LoanApplicationRequest.class)))
                .thenReturn(new ResponseEntity<>(DealTestData.expectedLoanOfferList, HttpStatus.OK));
        Assertions.assertEquals(DealTestData.expectedLoanOfferList,
                dealService.getPossibleLoanOffers(mock(LoanApplicationRequest.class)));
    }

    @Test
    void chooseOfferTest() {
        Mockito.when(applicationService.get(Mockito.any(Long.class))).thenReturn(DealTestData.application);
        dealService.chooseOffer(mock(LoanOffer.class));
        Mockito.verify(applicationService, times(1)).get(Mockito.any(Long.class));
    }

    @Test
    void calculateCreditConditionsTest() {
        when(applicationService.get(Mockito.any(Long.class))).thenReturn(DealTestData.application);
        when(clientService.enrichClientInformation(Mockito.any(Client.class),
                Mockito.any(FinishRegistrationRequest.class))).thenReturn(DealTestData.client);
        when(conveyorMSClient.calculatedCreditConditions(Mockito.any(ScoringDataDTO.class)))
                .thenReturn(new ResponseEntity<>(DealTestData.creditDTO, HttpStatus.OK));
        when(creditMapper.toCredit(DealTestData.creditDTO)).thenReturn(DealTestData.credit);
        dealService.calculateCreditConditions(mock(FinishRegistrationRequest.class), 1L);
        Mockito.verify(clientService, times(1)).enrichClientInformation(Mockito.any(Client.class),
                Mockito.any(FinishRegistrationRequest.class));

    }
}
