package com.github.nst1610.neoflex.project.dossier.client;

import com.github.nst1610.neoflex.project.dossier.model.Application;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "${feign-client.name}", url = "${feign-client.url}")
public interface DealMSClient {
    @GetMapping("/admin/application/{applicationId}")
    ResponseEntity<Application> getApplicationById(@PathVariable Long applicationId);

    @PutMapping("/admin/application/{applicationId}/status")
    ResponseEntity<Application> updateApplicationStatus(@PathVariable Long applicationId);
}
