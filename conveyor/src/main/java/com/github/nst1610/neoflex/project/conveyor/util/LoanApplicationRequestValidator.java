package com.github.nst1610.neoflex.project.conveyor.util;

import com.github.nst1610.neoflex.project.conveyor.model.LoanApplicationRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.Period;

@Component
public class LoanApplicationRequestValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return LoanApplicationRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LoanApplicationRequest loanApplicationRequest = (LoanApplicationRequest) target;
        if (Period.between(loanApplicationRequest.getBirthdate(), LocalDate.now()).getYears() < 20){
            errors.rejectValue("birthdate", "", "Возраст должен быть не меньше 20 лет.");
        }
    }
}
