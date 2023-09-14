package com.github.nst1610.neoflex.project.dossier.service;

import com.github.nst1610.neoflex.project.dossier.client.DealMSClient;
import com.github.nst1610.neoflex.project.dossier.exception.SendEmailException;
import com.github.nst1610.neoflex.project.dossier.model.Application;
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

    public void sendFinishRegistrationMessage(EmailMessage emailMessage) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(sender);
        message.setTo(emailMessage.getAddress());
        message.setSubject("Finish Registration");
        message.setText("Hello, your loan application №" + emailMessage.getApplicationId() + " approved.\n" +
                "Now you should finish registration.");

        mailSender.send(message);
    }

    public void sendCreateDocumentsMessage(EmailMessage emailMessage) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(sender);
        message.setTo(emailMessage.getAddress());
        message.setSubject("Create Documents");
        message.setText("Hello, your loan application №" + emailMessage.getApplicationId() + " passed all checks.\n" +
                "Now you should send creating documents request.");

        mailSender.send(message);
    }

    public void sendSendDocumentsMessage(EmailMessage emailMessage) {
        List<File> documents = documentsService.createAllDocuments(emailMessage.getApplicationId());
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(sender);
            helper.setTo(emailMessage.getAddress());
            helper.setSubject("Your Loan Documents");
            helper.setText("Hello, here it is your loan documents for application №" + emailMessage.getApplicationId()
                    + "! \nNow you should send signing documents request.");

            for (File document : documents) {
                helper.addAttachment(document.getName(), document);
            }
        } catch (MessagingException e) {
            throw new SendEmailException();
        }

        dealMSClient.updateApplicationStatus(emailMessage.getApplicationId());
        mailSender.send(message);
    }

    public void sendSesCodeMessage(EmailMessage emailMessage) {
        SimpleMailMessage message = new SimpleMailMessage();
        Application application = dealMSClient.getApplicationById(emailMessage.getApplicationId()).getBody();

        message.setFrom(sender);
        message.setTo(emailMessage.getAddress());
        message.setSubject("Sign Documents With SES Code");
        message.setText("Hello, here it is your Simple Electric Sign Code " + application.getSesCode() +
                " for your application №" + emailMessage.getApplicationId() + ".");

        mailSender.send(message);
    }

    public void sendCreditIssuedMessage(EmailMessage emailMessage) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(sender);
        message.setTo(emailMessage.getAddress());
        message.setSubject("Credit Issued");
        message.setText("Hello, credit for your application №" + emailMessage.getApplicationId() +
                " has already issued!" + "\nMoney will transfer to yor account soon." +
                "\nThanks!");

        mailSender.send(message);
    }
}
