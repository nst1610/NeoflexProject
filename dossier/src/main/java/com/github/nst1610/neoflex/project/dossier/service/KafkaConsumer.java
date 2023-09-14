package com.github.nst1610.neoflex.project.dossier.service;

import com.github.nst1610.neoflex.project.dossier.model.EmailMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {
    private final EmailService emailService;

    @KafkaListener(topics = "finish-registration", groupId = "dossier")
    public void finishRegistration(EmailMessage message) {
        emailService.sendFinishRegistrationMessage(message);
    }

    @KafkaListener(topics = "create-documents", groupId = "dossier")
    public void createDocuments(EmailMessage message) {
        emailService.sendCreateDocumentsMessage(message);
    }

    @KafkaListener(topics = "send-documents", groupId = "dossier")
    public void sendDocuments(EmailMessage message) {
        emailService.sendSendDocumentsMessage(message);
    }

    @KafkaListener(topics = "send-ses", groupId = "dossier")
    public void sendSes(EmailMessage message) {
        emailService.sendSesCodeMessage(message);
    }

    @KafkaListener(topics = "credit-issued", groupId = "dossier")
    public void creditIssued(EmailMessage message) {
        emailService.sendCreditIssuedMessage(message);
    }
}
