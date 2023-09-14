package com.github.nst1610.neoflex.project.gateway.api.controller;

import com.github.nst1610.neoflex.project.gateway.api.dto.FinishRegistrationRequest;
import com.github.nst1610.neoflex.project.gateway.api.dto.LoanApplicationRequest;
import com.github.nst1610.neoflex.project.gateway.api.dto.LoanOffer;
import com.github.nst1610.neoflex.project.gateway.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/application")
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<List<LoanOffer>> createLoanApplication(@RequestBody LoanApplicationRequest
                                                                             loanApplicationRequest) {
        return new ResponseEntity<>(applicationService.createLoanOfferForClient(loanApplicationRequest), HttpStatus.OK);
    }

    @PostMapping("/offer")
    public ResponseEntity<Void> chooseOffer(@RequestBody LoanOffer loanOffer) {
        applicationService.chooseOffer(loanOffer);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/registration/{applicationId}")
    public ResponseEntity<Void> finishRegistration(@PathVariable Long applicationId,
                                                   @RequestBody FinishRegistrationRequest finishRegistrationRequest) {
        applicationService.finishRegistration(applicationId, finishRegistrationRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
