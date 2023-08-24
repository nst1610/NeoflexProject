package com.github.nst1610.neoflex.project.application.service;

import com.github.nst1610.neoflex.project.application.api.dto.LoanApplicationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.github.nst1610.neoflex.project.application.util.Constant.*;
import static com.github.nst1610.neoflex.project.application.util.Message.*;
import static com.github.nst1610.neoflex.project.application.util.Pattern.*;

@Service
@Slf4j
public class PreScoringService {

    public Map<String, String> preScore(LoanApplicationRequest loanApplicationRequest){
        log.info("Начало прескоринга.");

        Map<String, String> incorrectField = new HashMap<>();
        if (Objects.isNull(loanApplicationRequest.getFirstName())
                || !loanApplicationRequest.getFirstName().matches(NAME_PATTERN)) {
            incorrectField.put("firstName", INCORRECT_NAME_MESSAGE);
        }
        if (Objects.isNull(loanApplicationRequest.getLastName())
                || !loanApplicationRequest.getLastName().matches(NAME_PATTERN)) {
            incorrectField.put("lastName", INCORRECT_NAME_MESSAGE);
        }
        if (Objects.nonNull(loanApplicationRequest.getMiddleName())
                && !loanApplicationRequest.getMiddleName().matches(NAME_PATTERN)) {
            incorrectField.put("middleName", INCORRECT_NAME_MESSAGE);
        }
        if(Objects.isNull(loanApplicationRequest.getAmount())
                || loanApplicationRequest.getAmount().compareTo(MIN_AMOUNT) < 0) {
            incorrectField.put("amount", INCORRECT_AMOUNT_MESSAGE);
        }
        if(Objects.isNull(loanApplicationRequest.getTerm())
                || loanApplicationRequest.getTerm() < MIN_TERM) {
            incorrectField.put("term", INCORRECT_TERM_MESSAGE);
        }
        if (Objects.isNull(loanApplicationRequest.getBirthDate())
                || Period.between(loanApplicationRequest.getBirthDate(), LocalDate.now()).getYears() < MIN_CLIENT_AGE) {
            incorrectField.put("birthDate", INCORRECT_AGE_MESSAGE);
        }
        if (Objects.isNull(loanApplicationRequest.getEmail())
                || !loanApplicationRequest.getEmail().matches(EMAIL_PATTERN)) {
            incorrectField.put("email", INCORRECT_EMAIL_MESSAGE);
        }
        if (Objects.isNull(loanApplicationRequest.getPassportSeries())
                || !loanApplicationRequest.getPassportSeries().matches(PASSPORT_SERIES_PATTERN)) {
            incorrectField.put("passportSeries", INCORRECT_PASSPORT_SERIES_MESSAGE);
        }
        if (Objects.isNull(loanApplicationRequest.getPassportNumber())
                || !loanApplicationRequest.getPassportNumber().matches(PASSPORT_NUMBER_PATTERN)) {
            incorrectField.put("passportNumber", INCORRECT_PASSPORT_NUMBER_MESSAGE);
        }

        log.info("Прескоринг окончен.");
        return incorrectField;
    }
}
