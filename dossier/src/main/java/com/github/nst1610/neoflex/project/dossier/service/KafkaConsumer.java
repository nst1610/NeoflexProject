package com.github.nst1610.neoflex.project.dossier.service;

import com.github.nst1610.neoflex.project.dossier.model.EmailMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {
    private final EmailService emailService;

    @KafkaListener(topics = {"finish-registration", "create-documents", "send-documents", "send-ses",
    "credit-issued"}, groupId = "dossier")
    public void finishRegistration(EmailMessage message) {
        emailService.sendMessage(message);
    }
}
