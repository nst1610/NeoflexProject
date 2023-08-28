package com.github.nst1610.neoflex.project.application;

import com.github.nst1610.neoflex.project.application.api.dto.LoanApplicationRequest;
import com.github.nst1610.neoflex.project.application.api.dto.LoanOffer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ApplicationTestData {
    public static LoanApplicationRequest loanApplicationRequest;
    public static LoanApplicationRequest invalidLoanApplicationRequest;
    public static List<LoanOffer> loanOfferList;

    static {
        loanApplicationRequest = LoanApplicationRequest.builder()
                .amount(BigDecimal.valueOf(500000))
                .term(12)
                .firstName("Ivan")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .email("ivan@mail.ru")
                .birthDate(LocalDate.parse("1911-11-11"))
                .passportSeries("1111")
                .passportNumber("222222")
                .build();
        invalidLoanApplicationRequest = LoanApplicationRequest.builder()
                .amount(BigDecimal.valueOf(100)) // incorrect field
                .term(12)
                .firstName("Ivan")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .email("ivan@mail.ru")
                .birthDate(LocalDate.parse("1999-11-11"))
                .passportSeries("1111")
                .passportNumber("222222")
                .build();

        loanOfferList = List.of(
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
    }
}
