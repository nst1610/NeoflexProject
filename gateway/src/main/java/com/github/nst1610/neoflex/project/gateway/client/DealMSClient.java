package com.github.nst1610.neoflex.project.gateway.client;

import com.github.nst1610.neoflex.project.gateway.api.dto.FinishRegistrationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "${feign-clients.deal-client.name}", url = "${feign-clients.deal-client.url}")
public interface DealMSClient {
    @PutMapping("/calculate/{applicationId}")
    ResponseEntity<Void> calculateCreditConditions(@PathVariable Long applicationId,
                                                   @RequestBody FinishRegistrationRequest finishRegistrationRequest);

    @PutMapping("/document/{applicationId}/send")
    ResponseEntity<Void> sendDocuments(@PathVariable Long applicationId);

    @PutMapping("/document/{applicationId}/sign")
    ResponseEntity<Void> signDocumentsRequest(@PathVariable Long applicationId);

    @PutMapping("/document/{applicationId}/code")
    ResponseEntity<Void> verifySesCode(@PathVariable Long applicationId,
                                              @RequestBody String sesCode);
}
