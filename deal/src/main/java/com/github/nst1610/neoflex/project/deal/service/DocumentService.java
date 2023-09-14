package com.github.nst1610.neoflex.project.deal.service;

import com.github.nst1610.neoflex.project.deal.api.exception.ApplicationStatusException;
import com.github.nst1610.neoflex.project.deal.api.exception.VerifySesCodeException;
import com.github.nst1610.neoflex.project.deal.model.EmailMessage;
import com.github.nst1610.neoflex.project.deal.model.enums.ApplicationStatus;
import com.github.nst1610.neoflex.project.deal.model.enums.CreditStatus;
import com.github.nst1610.neoflex.project.deal.model.enums.Theme;
import com.github.nst1610.neoflex.project.deal.repository.entity.Application;
import com.github.nst1610.neoflex.project.deal.repository.entity.Credit;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.github.nst1610.neoflex.project.deal.model.enums.ApplicationStatus.*;
import static com.github.nst1610.neoflex.project.deal.model.enums.Theme.*;
import static com.github.nst1610.neoflex.project.deal.model.enums.Theme.CREDIT_ISSUED;

@Service
@RequiredArgsConstructor
public class DocumentService {
    private final KafkaTemplate<String, EmailMessage> kafkaTemplate;
    private final ApplicationService applicationService;
    private final DealService dealService;
    private final CreditService creditService;

    public void finishRegistration(Long applicationId) {
        sendMessage(applicationId, FINISH_REGISTRATION, "finish-registration");
    }

    public void createDocuments(Long applicationId) {
        sendMessage(applicationId, CREATE_DOCUMENTS, "create-documents");
    }

    public void sendDocuments(Long applicationId) {
        Application application = applicationService.get(applicationId);

        if (!application.getStatus().equals(CC_APPROVED)) {
            throw new ApplicationStatusException("Application status should be equals " + CC_APPROVED
                    + ". Current application status is " + application.getStatus() + ".");
        }

        dealService.updateStatusHistory(application, PREPARE_DOCUMENTS);
        applicationService.save(application);
        sendMessage(applicationId, SEND_DOCUMENTS, "send-documents");
    }

    public void signDocumentsRequest(Long applicationId) {
        Application application = applicationService.get(applicationId);

        if (!application.getStatus().equals(DOCUMENT_CREATED)) {
            throw new ApplicationStatusException("Application status should be equals " + DOCUMENT_CREATED
                    + ". Current application status is " + application.getStatus() + ".");
        }

        application.setSesCode(generateSesCode());
        applicationService.save(application);
        sendMessage(applicationId, SEND_SES, "send-ses");
    }

    public void sendCreditIssuedRequest(Long applicationId, String sesCode) {
        Application application = applicationService.get(applicationId);

        if (!application.getStatus().equals(DOCUMENT_CREATED)) {
            throw new ApplicationStatusException("Application status should be equals " + DOCUMENT_CREATED
                    + ". Current application status is " + application.getStatus() + ".");
        }

        verifySesCode(application, sesCode);

        dealService.updateStatusHistory(application, DOCUMENT_SIGNED);
        applicationService.save(application);

        Credit credit = application.getCredit();
        credit.setCreditStatus(CreditStatus.ISSUED);
        creditService.save(credit);

        dealService.updateStatusHistory(application, ApplicationStatus.CREDIT_ISSUED);
        applicationService.save(application);

        sendMessage(applicationId, CREDIT_ISSUED, "credit-issued");

    }

    private void sendMessage(Long applicationId, Theme theme, String topic) {
        Application application = applicationService.get(applicationId);
        EmailMessage message = EmailMessage.builder()
                .address(application.getClient().getEmail())
                .theme(theme)
                .applicationId(applicationId)
                .build();
        kafkaTemplate.send(topic, message);
    }

    private String generateSesCode() {
        return String.valueOf((int)(1 + Math.random() * 999999));
    }

    private void verifySesCode(Application application, String sesCode) {
        if (!application.getSesCode().equals(sesCode)) {
            throw new VerifySesCodeException();
        }
    }

}
