package com.github.nst1610.neoflex.project.deal.client;

import com.github.nst1610.neoflex.project.deal.dto.CreditDTO;
import com.github.nst1610.neoflex.project.deal.dto.LoanApplicationRequestDTO;
import com.github.nst1610.neoflex.project.deal.dto.LoanOfferDTO;
import com.github.nst1610.neoflex.project.deal.dto.ScoringDataDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "${feign-client.name}", url = "${feign-client.url}")
public interface ConveyorMSClient {
    @PostMapping("/offers")
    ResponseEntity<List<LoanOfferDTO>> possibleOffersForClient(@RequestBody LoanApplicationRequestDTO loanApplicationRequest);

    @PostMapping("/calculation")
    ResponseEntity<CreditDTO> calculatedCreditConditions(@RequestBody ScoringDataDTO scoringDataDTO);
}
