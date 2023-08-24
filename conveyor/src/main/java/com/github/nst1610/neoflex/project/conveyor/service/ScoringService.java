package com.github.nst1610.neoflex.project.conveyor.service;

import com.github.nst1610.neoflex.project.conveyor.dto.EmploymentDTO;
import com.github.nst1610.neoflex.project.conveyor.dto.ScoringData;
import com.github.nst1610.neoflex.project.conveyor.exception.ScoringException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

import static com.github.nst1610.neoflex.project.conveyor.util.Constant.*;

@Service
@Slf4j
public class ScoringService {

    public void validateScoringData(ScoringData scoringData){
        log.info("Начало скоринга.");

        validateAmount(scoringData.getAmount(), scoringData.getEmployment().getSalary());
        validateAge(scoringData.getBirthDate());
        validateEmploymentStatus(scoringData.getEmployment().getEmploymentStatus());
        validatePosition(scoringData.getEmployment().getPosition());
        validateExperience(scoringData.getEmployment().getWorkExperienceTotal(),
                scoringData.getEmployment().getWorkExperienceCurrent());

        log.info("Скоринг завершен.");
    }

    private void validateAmount(BigDecimal amount, BigDecimal salary){
        if(salary.multiply(BigDecimal.valueOf(MAX_SALARY_COUNT)).compareTo(amount) < 0){
            throw new ScoringException("Сумма кредита превышает размер 20 зарплат.");
        }
    }

    private void validateAge(LocalDate birthDate){
        int age = Period.between(birthDate, LocalDate.now()).getYears();
        if(age < MIN_CLIENT_AGE){
            throw new ScoringException("Возраст меньше 20 лет.");
        } else if (age > MAX_CLIENT_AGE) {
            throw new ScoringException("Возраст больше 60 лет");
        }
    }

    private void validateEmploymentStatus(EmploymentDTO.EmploymentStatusEnum status){
        if (status.equals(EmploymentDTO.EmploymentStatusEnum.UNEMPLOYED)){
            throw new ScoringException("Рабочий статус - безработный.");
        }
    }

    private void validatePosition(EmploymentDTO.PositionEnum position){
        if(position.equals(EmploymentDTO.PositionEnum.INTERN)){
            throw new ScoringException("Позиция на работе - стажер.");
        }
    }

    private void validateExperience(Integer workExperienceTotal, Integer workExperienceCurrent){
        if(workExperienceTotal < MIN_TOTAL_WORK_EXPERIENCE){
            throw new ScoringException("Общий стаж работы менее 12 месяцев");
        } else if (workExperienceCurrent < MIN_CURRENT_WORK_EXPERIENCE) {
            throw new ScoringException("Текщий стаж работы менее 3 месяцев");
        }
    }
}
