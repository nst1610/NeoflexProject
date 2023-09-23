package com.github.nst1610.neoflex.project.dossier.service;

import com.github.nst1610.neoflex.project.dossier.client.DealMSClient;
import com.github.nst1610.neoflex.project.dossier.model.Application;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {
    public static final String FINISH_REGISTRATION_SUBJECT = "Finish Registration";
    public static final String CREATE_DOCUMENTS_SUBJECT = "Create Documents";
    public static final String SEND_DOCUMENTS_SUBJECT = "Your Loan Documents";
    public static final String SEND_SES_SUBJECT = "Sign Documents With SES Code";
    public static final String CREDIT_ISSUED_SUBJECT = "Credit Issued";

    private final DealMSClient dealMSClient;

    public String finishRegistrationMessage(Long applicationId) {
        return "Hello, your loan application №" + applicationId + " approved.\n" +
                "Now you should finish registration.";
    }

    public String createDocumentsMessage(Long applicationId) {
        return "Hello, your loan application №" + applicationId + " passed all checks.\n" +
                "Now you should send creating documents request.";
    }

    public String sendDocumentsMessage(Long applicationId) {
        return "Hello, here it is your loan documents for application №" + applicationId
                + "! \nNow you should send signing documents request.";
    }

    public String sendSesCodeMessage(Long applicationId) {
        Application application = dealMSClient.getApplicationById(applicationId).getBody();
        return "Hello, here it is your Simple Electric Sign Code " + application.getSesCode() +
                " for your application №" + applicationId + ".";
    }

    public String creditIssuedMessage(Long applicationId) {
        return "Hello, credit for your application №" + applicationId +
                " has already issued!" + "\nMoney will transfer to yor account soon." +
                "\nThanks!";
    }
}
