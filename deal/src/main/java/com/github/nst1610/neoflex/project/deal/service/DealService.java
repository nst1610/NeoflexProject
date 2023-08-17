package com.github.nst1610.neoflex.project.deal.service;

import com.github.nst1610.neoflex.project.deal.client.ConveyorMSClient;
import com.github.nst1610.neoflex.project.deal.dto.*;
import com.github.nst1610.neoflex.project.deal.entity.Application;
import com.github.nst1610.neoflex.project.deal.entity.Client;
import com.github.nst1610.neoflex.project.deal.entity.Credit;
import com.github.nst1610.neoflex.project.deal.mapper.ClientMapper;
import com.github.nst1610.neoflex.project.deal.mapper.CreditMapper;
import com.github.nst1610.neoflex.project.deal.mapper.EmploymentMapper;
import com.github.nst1610.neoflex.project.deal.model.ApplicationStatusHistory;
import com.github.nst1610.neoflex.project.deal.model.Passport;
import com.github.nst1610.neoflex.project.deal.model.enums.ApplicationStatus;
import com.github.nst1610.neoflex.project.deal.model.enums.ChangeType;
import com.github.nst1610.neoflex.project.deal.model.enums.CreditStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class DealService {
    private final ClientMapper clientMapper;
    private final EmploymentMapper employmentMapper;
    private final CreditMapper creditMapper;
    private final ClientService clientService;
    private final CreditService creditService;
    private final ApplicationService applicationService;
    private final ConveyorMSClient conveyorMSClient;

    public List<LoanOfferDTO> getPossibleLoanOffers(LoanApplicationRequestDTO loanApplicationRequest) {
        log.info("Формирование возможных кредитных предложений для клиента.");

        Client client = clientService.createClientFromLoanApplicationRequest(loanApplicationRequest);
        clientService.save(client);
        log.info("Клиент сохранен.");

        Application application = Application.builder()
                .client(client)
                .creationDate(LocalDate.now())
                .build();
        updateStatusHistory(application, ApplicationStatus.PREAPPROVAL);
        log.info("Создана заявка {}", application);

        applicationService.save(application);
        log.info("Заявка сохранена.");

        List<LoanOfferDTO> offers = conveyorMSClient.possibleOffersForClient(loanApplicationRequest).getBody();
        offers.forEach(offer -> offer.setApplicationId(application.getApplicationId()));
        log.info("Возожные кредитные предложения сформированы.");

        return offers;
    }

    public void chooseOffer(LoanOfferDTO loanOffer) {
        log.info("Выбрано кредитное предложение.");
        Application application = applicationService.get(loanOffer.getApplicationId());
        updateStatusHistory(application, ApplicationStatus.APPROVED);
        application.setAppliedOffer(loanOffer);
        log.info("Заявке назначено выбранное кредитное предложение.");
        applicationService.save(application);
        log.info("Заявка сохранена.");
    }

    public void calculateCreditConditions(FinishRegistrationRequestDTO finishRegistrationRequest, Long applicationId) {
        log.info("Начало расчета параметров кредита для заявки " + applicationId + ".");
        Application application = applicationService.get(applicationId);

        Client client = clientService.enrichClientInformation(application.getClient(),
                finishRegistrationRequest);
        clientService.save(client);
        log.info("Расширены данные о клиенте.");

        Credit credit = creditMapper.toModel(conveyorMSClient.calculatedCreditConditions(createScoringData(client,
                application)).getBody());

        credit.setCreditStatus(CreditStatus.CALCULATED);
        credit.setApplication(application);
        creditService.save(credit);
        log.info("Кредит сохранен.");

        application.setCredit(credit);
        updateStatusHistory(application, ApplicationStatus.CC_APPROVED);
        applicationService.save(application);
        log.info("Заявка сохранена.");
    }

    public void updateStatusHistory(Application application, ApplicationStatus status) {
        application.setStatus(status);

        if(Objects.isNull(application.getStatusHistory())) {
            application.setStatusHistory(new ArrayList<>());
        }

        ApplicationStatusHistory statusHistory = ApplicationStatusHistory.builder()
                .status(status)
                .time(LocalDate.now())
                .changeType(ChangeType.AUTOMATIC)
                .build();

        application.getStatusHistory().add(statusHistory);

        log.info("Статус заявки изменен на " + status);
    }

    private ScoringDataDTO createScoringData(Client client, Application application) {
        return ScoringDataDTO.builder()
                .amount(application.getAppliedOffer().getTotalAmount())
                .term(application.getAppliedOffer().getTerm())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .middleName(client.getMiddleName())
                .gender(client.getGender())
                .birthDate(client.getBirthDate())
                .passportSeries(client.getPassport().getSeries())
                .passportNumber(client.getPassport().getNumber())
                .passportIssueBranch(client.getPassport().getIssueBranch())
                .passportIssueDate(client.getPassport().getIssueDate())
                .maritalStatus(client.getMaritalStatus())
                .dependentAmount(client.getDependentAmount())
                .employment(employmentMapper.toDto(client.getEmployment()))
                .account(client.getAccount())
                .isInsuranceEnabled(application.getAppliedOffer().getIsInsuranceEnabled())
                .isSalaryClient(application.getAppliedOffer().getIsSalaryClient())
                .build();
    }
}
