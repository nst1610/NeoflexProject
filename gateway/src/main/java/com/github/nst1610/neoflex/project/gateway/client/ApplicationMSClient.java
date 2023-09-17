package com.github.nst1610.neoflex.project.gateway.client;

import com.github.nst1610.neoflex.project.gateway.api.dto.LoanApplicationRequest;
import com.github.nst1610.neoflex.project.gateway.api.dto.LoanOffer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "${feign-clients.application-client.name}", url = "${feign-clients.application-client.url}")
public interface ApplicationMSClient {
    @PostMapping("/application")
    ResponseEntity<List<LoanOffer>> calculatePossibleOffers(@RequestBody LoanApplicationRequest
                                                                           loanApplicationRequest);

    @PutMapping("/application/offer")
    ResponseEntity<Void> chooseOffer(@RequestBody LoanOffer loanOffer);
}
