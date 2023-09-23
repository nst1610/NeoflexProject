package com.github.nst1610.neoflex.project.deal;

import com.github.nst1610.neoflex.project.deal.api.dto.CreditDTO;
import com.github.nst1610.neoflex.project.deal.api.dto.LoanOffer;
import com.github.nst1610.neoflex.project.deal.repository.entity.Application;
import com.github.nst1610.neoflex.project.deal.repository.entity.Client;
import com.github.nst1610.neoflex.project.deal.repository.entity.Credit;
import com.github.nst1610.neoflex.project.deal.model.Employment;
import com.github.nst1610.neoflex.project.deal.model.Passport;
import com.github.nst1610.neoflex.project.deal.model.enums.ApplicationStatus;
import com.github.nst1610.neoflex.project.deal.model.enums.Gender;
import com.github.nst1610.neoflex.project.deal.model.enums.MaritalStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

public class DealTestData {
    public static List<LoanOffer> expectedLoanOfferList;
    public static Application application;
    public static Client client;
    public static CreditDTO creditDTO;
    public static Credit credit;

    static {
        expectedLoanOfferList = List.of(
                LoanOffer.builder().applicationId(1L)
                        .requestedAmount(BigDecimal.valueOf(100000.00))
                        .totalAmount(BigDecimal.valueOf(108000.00))
                        .term(12)
                        .monthlyPayment(BigDecimal.valueOf(9000.00))
                        .rate(BigDecimal.valueOf(11))
                        .isInsuranceEnabled(false)
                        .isSalaryClient(false)
                        .build(),

                LoanOffer.builder().applicationId(1L)
                        .requestedAmount(BigDecimal.valueOf(100000.00))
                        .totalAmount(BigDecimal.valueOf(108000.00))
                        .term(12)
                        .monthlyPayment(BigDecimal.valueOf(9000.00))
                        .rate(BigDecimal.valueOf(8))
                        .isInsuranceEnabled(false)
                        .isSalaryClient(true)
                        .build(),

                LoanOffer.builder().applicationId(1L)
                        .requestedAmount(BigDecimal.valueOf(100000.00))
                        .totalAmount(BigDecimal.valueOf(108000.00))
                        .term(12)
                        .monthlyPayment(BigDecimal.valueOf(9000.00))
                        .rate(BigDecimal.valueOf(8))
                        .isInsuranceEnabled(true)
                        .isSalaryClient(false)
                        .build(),

                LoanOffer.builder().applicationId(1L)
                        .requestedAmount(BigDecimal.valueOf(100000.00))
                        .totalAmount(BigDecimal.valueOf(108000.00))
                        .term(12)
                        .monthlyPayment(BigDecimal.valueOf(9000.00))
                        .rate(BigDecimal.valueOf(5))
                        .isInsuranceEnabled(true)
                        .isSalaryClient(false)
                        .build());

        application = Application.builder()
                .applicationId(1L)
                .appliedOffer(mock(LoanOffer.class))
                .client(mock(Client.class))
                .creationDate(LocalDateTime.now())
                .status(ApplicationStatus.PREAPPROVAL)
                .statusHistory(new ArrayList<>())
                .build();

        client = Client.builder()
                .clientId(1L)
                .firstName("Ivan")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .birthDate(LocalDate.parse("1995-11-11"))
                .email("ivan@mail.ru")
                .gender(Gender.FEMALE)
                .maritalStatus(MaritalStatus.SINGLE)
                .dependentAmount(0)
                .passport(mock(Passport.class))
                .employment(mock(Employment.class))
                .account("55555")
                .application(mock(Application.class))
                .build();

        creditDTO = CreditDTO.builder()
                .psk(BigDecimal.valueOf(100500))
                .rate(BigDecimal.valueOf(15))
                .monthlyPayment(BigDecimal.valueOf(100500))
                .amount(BigDecimal.valueOf(100200))
                .isInsuranceEnabled(true)
                .isSalaryClient(true)
                .paymentSchedule(new ArrayList<>())
                .term(60)
                .build();

        credit = Credit.builder()
                .psk(BigDecimal.valueOf(100500))
                .rate(BigDecimal.valueOf(15))
                .monthlyPayment(BigDecimal.valueOf(100500))
                .amount(BigDecimal.valueOf(100200))
                .isInsuranceEnabled(true)
                .isSalaryClient(true)
                .paymentSchedule(new ArrayList<>())
                .term(60)
                .build();
    }
}
