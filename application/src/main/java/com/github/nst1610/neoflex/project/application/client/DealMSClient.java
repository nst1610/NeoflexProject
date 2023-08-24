package com.github.nst1610.neoflex.project.application.client;

import com.github.nst1610.neoflex.project.application.api.dto.LoanApplicationRequest;
import com.github.nst1610.neoflex.project.application.api.dto.LoanOffer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "${feign-client.name}", url = "${feign-client.url}")
public interface DealMSClient {
    @PostMapping("/application")
    ResponseEntity<List<LoanOffer>> calculatePossibleOffers(@RequestBody LoanApplicationRequest
                                                                       loanApplicationRequest);

    @PutMapping("/offer")
    ResponseEntity<Void> chooseOffer(@RequestBody LoanOffer loanOffer);
}
