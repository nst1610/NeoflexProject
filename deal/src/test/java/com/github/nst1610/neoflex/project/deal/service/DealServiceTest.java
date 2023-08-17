package com.github.nst1610.neoflex.project.deal.service;

import com.github.nst1610.neoflex.project.deal.DealTestData;
import com.github.nst1610.neoflex.project.deal.client.ConveyorMSClient;
import com.github.nst1610.neoflex.project.deal.dto.*;
import com.github.nst1610.neoflex.project.deal.entity.Application;
import com.github.nst1610.neoflex.project.deal.entity.Client;
import com.github.nst1610.neoflex.project.deal.entity.Credit;
import com.github.nst1610.neoflex.project.deal.mapper.ClientMapper;
import com.github.nst1610.neoflex.project.deal.mapper.CreditMapper;
import com.github.nst1610.neoflex.project.deal.mapper.EmploymentMapper;
import com.github.nst1610.neoflex.project.deal.model.Employment;
import com.github.nst1610.neoflex.project.deal.model.Passport;
import com.github.nst1610.neoflex.project.deal.model.enums.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
        Mockito.when(conveyorMSClient.possibleOffersForClient(Mockito.any(LoanApplicationRequestDTO.class)))
                .thenReturn(new ResponseEntity<>(DealTestData.expectedLoanOfferList, HttpStatus.OK));
        Assertions.assertEquals(DealTestData.expectedLoanOfferList,
                dealService.getPossibleLoanOffers(mock(LoanApplicationRequestDTO.class)));
    }

    @Test
    void chooseOfferTest() {
        Mockito.when(applicationService.get(Mockito.any(Long.class))).thenReturn(DealTestData.application);
        dealService.chooseOffer(mock(LoanOfferDTO.class));
        Mockito.verify(applicationService, times(1)).get(Mockito.any(Long.class));
    }

    @Test
    void calculateCreditConditionsTest() {
        when(applicationService.get(Mockito.any(Long.class))).thenReturn(DealTestData.application);
        when(clientService.enrichClientInformation(Mockito.any(Client.class),
                Mockito.any(FinishRegistrationRequestDTO.class))).thenReturn(DealTestData.client);
        when(conveyorMSClient.calculatedCreditConditions(Mockito.any(ScoringDataDTO.class)))
                .thenReturn(new ResponseEntity<>(DealTestData.creditDTO, HttpStatus.OK));
        when(creditMapper.toModel(DealTestData.creditDTO)).thenReturn(DealTestData.credit);
        dealService.calculateCreditConditions(mock(FinishRegistrationRequestDTO.class), 1L);
        Mockito.verify(clientService, times(1)).enrichClientInformation(Mockito.any(Client.class),
                Mockito.any(FinishRegistrationRequestDTO.class));

    }
}
