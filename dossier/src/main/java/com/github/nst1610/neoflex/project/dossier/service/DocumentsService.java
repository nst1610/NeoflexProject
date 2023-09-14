package com.github.nst1610.neoflex.project.dossier.service;

import com.github.nst1610.neoflex.project.dossier.client.DealMSClient;
import com.github.nst1610.neoflex.project.dossier.exception.CreatingFileException;
import com.github.nst1610.neoflex.project.dossier.model.Application;
import com.github.nst1610.neoflex.project.dossier.model.Client;
import com.github.nst1610.neoflex.project.dossier.model.Credit;
import com.github.nst1610.neoflex.project.dossier.model.Employment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DocumentsService {
    private final DealMSClient client;

    public List<File> createAllDocuments(Long applicationId) {
        Application application = client.getApplicationById(applicationId).getBody();
        List<File> documents = new ArrayList<>();
        documents.add(createCreditApplicationFile(application));
        documents.add(createCreditContractFile(application));
        documents.add(createPaymentScheduleFile(application));

        return documents;
    }
    private File createCreditApplicationFile(Application application) {
        File file;
        try {
            file = File.createTempFile("credit-application", ".txt");

            try(FileWriter writer = new FileWriter(file)) {
                Client client = application.getClient();
                Employment employment = client.getEmployment();
                String text = new StringBuilder()
                        .append("Credit application №").append(application.getApplicationId())
                        .append(" from ").append(application.getCreationDate())
                        .append("\nClient info:")
                        .append("\n full name: ").append(client.getFirstName()).append(" ")
                        .append(Objects.nonNull(client.getMiddleName()) ? client.getMiddleName() : "").append(" ")
                        .append(client.getLastName())
                        .append("\n birthdate: ").append(client.getBirthDate())
                        .append("\n gender: ").append(client.getGender())
                        .append("\n passport: ").append(client.getPassport().getSeries()).append(" ").append(client.getPassport().getNumber())
                        .append("\n email: ").append(client.getEmail())
                        .append("\n marital status: ").append(client.getMaritalStatus())
                        .append("\n dependent amount: ").append(client.getDependentAmount())
                        .append("\n Employment: ")
                        .append("\n  employment status: ").append(employment.getEmploymentStatus())
                        .append("\n  employerINN: ").append(employment.getEmployerINN())
                        .append("\n  salary: ").append(employment.getSalary())
                        .append("\n  employment position: ").append(employment.getPosition())
                        .append("\n  total work experience: ").append(employment.getWorkExperienceTotal())
                        .append("\n  current work experience: ").append(employment.getWorkExperienceCurrent())
                        .toString();
                writer.write(text);
            }
        } catch (IOException e) {
            throw new CreatingFileException();
        }

        return file;
    }

    private File createCreditContractFile(Application application) {
        File file;
        try {
            file = File.createTempFile("credit-contract", ".txt");

            try(FileWriter writer = new FileWriter(file)) {
                Client client = application.getClient();
                Credit credit = application.getCredit();
                String text = new StringBuilder()
                        .append("Credit contract №").append(application.getApplicationId())
                        .append(" from ").append(application.getCreationDate())
                        .append("\nClient's full name: ")
                        .append("full name: ").append(client.getFirstName()).append(" ")
                        .append(Objects.nonNull(client.getMiddleName()) ? client.getMiddleName() : "").append(" ")
                        .append(client.getLastName())
                        .append("\n passport: ").append(client.getPassport().getSeries()).append(" ").append(client.getPassport().getNumber())
                        .append("\nCreditInfo: ")
                        .append("\n amount: ").append(credit.getAmount())
                        .append("\n term: ").append(credit.getTerm())
                        .append("\n monthly payment: ").append(credit.getMonthlyPayment())
                        .append("\n rate: ").append(credit.getRate())
                        .append("\n psk: ").append(credit.getPsk())
                        .append("\n services: ")
                        .append("\n  insurance: ").append(credit.getIsInsuranceEnabled())
                        .append("\n  salary client: ").append(credit.getIsSalaryClient())
                        .toString();
                writer.write(text);
            }
        } catch (IOException e) {
            throw new CreatingFileException();
        }

        return file;
    }

    private File createPaymentScheduleFile(Application application) {
        File file;
        try {
            file = File.createTempFile("payment-schedule", ".txt");

            try(FileWriter writer = new FileWriter(file)) {
                String text = application.getCredit().getPaymentSchedule().toString();
                writer.write(text);
            }
        } catch (IOException e) {
            throw new CreatingFileException();
        }
        return file;
    }
}
