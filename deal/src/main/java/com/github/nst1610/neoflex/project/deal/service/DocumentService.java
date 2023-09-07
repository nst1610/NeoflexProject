package com.github.nst1610.neoflex.project.deal.service;

import com.github.nst1610.neoflex.project.deal.model.EmailMessage;
import com.github.nst1610.neoflex.project.deal.model.enums.ApplicationStatus;
import com.github.nst1610.neoflex.project.deal.model.enums.Theme;
import com.github.nst1610.neoflex.project.deal.repository.entity.Application;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Random;

import static com.github.nst1610.neoflex.project.deal.model.enums.ApplicationStatus.PREPARE_DOCUMENTS;
import static com.github.nst1610.neoflex.project.deal.model.enums.Theme.*;

@RequiredArgsConstructor
public class DocumentService {
    private final KafkaTemplate<String, EmailMessage> kafkaTemplate;
    private final ApplicationService applicationService;
    private final DealService dealService;

    public void finishRegistration(Long applicationId) {
        sendMessage(applicationId, FINISH_REGISTRATION, "finish-registration");
    }

    public void createDocuments(Long applicationId) {
        sendMessage(applicationId, CREATE_DOCUMENTS, "create-documents");
    }

    public void sendDocuments(Long applicationId) {
        Application application = applicationService.get(applicationId);
        dealService.updateStatusHistory(application, PREPARE_DOCUMENTS);
        applicationService.save(application);
        sendMessage(applicationId, SEND_DOCUMENTS, "send-documents");
    }

    public void signDocuments(Long applicationId) {
        Application application = applicationService.get(applicationId);
        application.setSesCode(generateSesCode());
        applicationService.save(application);
        sendMessage(applicationId, SEND_SES, "send-ses");
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
        return String.valueOf(new Random().nextInt(1000, 9999));
    }

}
