package com.github.nst1610.neoflex.project.gateway.service;

import com.github.nst1610.neoflex.project.gateway.client.DealMSClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocumentService {
    private final DealMSClient dealMSClient;

    public void createDocumentsRequest(Long applicationId) {
        dealMSClient.sendDocuments(applicationId);
    }

    public void signDocumentsRequest(Long applicationId) {
        dealMSClient.signDocumentsRequest(applicationId);
    }

    public void verifySesCodeRequest(Long applicationId, String sesCode) {
        dealMSClient.verifySesCode(applicationId, sesCode);
    }
}
