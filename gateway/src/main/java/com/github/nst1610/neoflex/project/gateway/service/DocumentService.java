package com.github.nst1610.neoflex.project.gateway.service;

import com.github.nst1610.neoflex.project.gateway.client.DealMSClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentService {
    private final DealMSClient dealMSClient;

    public void createDocumentsRequest(Long applicationId) {
        log.info("Запрос на формирование документов для заявки №{}", applicationId);
        dealMSClient.sendDocuments(applicationId);
    }

    public void signDocumentsRequest(Long applicationId) {
        log.info("Запрос на подписание документов для заявки №{}", applicationId);
        dealMSClient.signDocumentsRequest(applicationId);
    }

    public void verifySesCodeRequest(Long applicationId, String sesCode) {
        log.info("Запрос на верификацию ses кода для заявки №{}", applicationId);
        dealMSClient.verifySesCode(applicationId, sesCode);
    }
}
