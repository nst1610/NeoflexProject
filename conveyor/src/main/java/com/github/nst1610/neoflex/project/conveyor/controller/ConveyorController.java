package com.github.nst1610.neoflex.project.conveyor.controller;

import com.github.nst1610.neoflex.project.conveyor.dto.*;
import com.github.nst1610.neoflex.project.conveyor.service.CreditService;
import com.github.nst1610.neoflex.project.conveyor.service.LoanOfferService;
import javax.validation.Valid;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class ConveyorController implements ConveyorApi {
    private final LoanOfferService loanOfferService;
    private final CreditService creditService;

    @Override
    public ResponseEntity<List<LoanOfferDTO>> possibleOffersForClient(
            @Valid @RequestBody LoanApplicationRequestDTO loanApplicationRequest
    ){
        return new ResponseEntity<>(loanOfferService.createLoanOfferForClient(loanApplicationRequest),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CreditDTO> calculatedCreditConditions(
            @Valid @RequestBody ScoringDataDTO scoringData
    ){
        return new ResponseEntity<>(creditService.getCalculatedCreditOffer(scoringData),
                HttpStatus.OK);
    }
}
