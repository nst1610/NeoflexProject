package com.github.nst1610.neoflex.project.dossier.service;

import com.github.nst1610.neoflex.project.dossier.client.DealMSClient;
import com.github.nst1610.neoflex.project.dossier.exception.SendEmailException;
import com.github.nst1610.neoflex.project.dossier.model.EmailMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailService {
    @Value("${spring.mail.username}")
    private String sender;
    private final JavaMailSender mailSender;
    private final DocumentsService documentsService;
    private final DealMSClient dealMSClient;
    private final MessageService messageService;

    public void sendMessage(EmailMessage emailMessage) {
        switch (emailMessage.getTheme()) {
            case FINISH_REGISTRATION -> simpleMessage(emailMessage, MessageService.FINISH_REGISTRATION_SUBJECT,
                    messageService.finishRegistrationMessage(emailMessage.getApplicationId()));
            case CREATE_DOCUMENTS -> simpleMessage(emailMessage, MessageService.CREATE_DOCUMENTS_SUBJECT,
                    messageService.createDocumentsMessage(emailMessage.getApplicationId()));
            case SEND_DOCUMENTS -> mimeMessage(emailMessage, MessageService.SEND_DOCUMENTS_SUBJECT,
                    messageService.sendDocumentsMessage(emailMessage.getApplicationId()),
                    documentsService.createAllDocuments(emailMessage.getApplicationId()));
            case SEND_SES -> simpleMessage(emailMessage, MessageService.SEND_SES_SUBJECT,
                    messageService.sendSesCodeMessage(emailMessage.getApplicationId()));
            case CREDIT_ISSUED -> simpleMessage(emailMessage, MessageService.CREDIT_ISSUED_SUBJECT,
                    messageService.creditIssuedMessage(emailMessage.getApplicationId()));
        }
    }

    private void mimeMessage(EmailMessage emailMessage, String subject, String text, List<File> documents) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(sender);
            helper.setTo(emailMessage.getAddress());
            helper.setSubject(subject);
            helper.setText(text);
            for (File document : documents) {
                helper.addAttachment(document.getName(), document);
            }
        } catch (MessagingException e) {
            throw new SendEmailException();
        }
        dealMSClient.updateApplicationStatus(emailMessage.getApplicationId());
        mailSender.send(message);
    }

    private void simpleMessage(EmailMessage emailMessage, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(sender);
        message.setTo(emailMessage.getAddress());
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }
}
