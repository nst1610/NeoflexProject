package com.github.nst1610.neoflex.project.conveyor.dto;

import com.github.nst1610.neoflex.project.conveyor.constant.Gender;
import com.github.nst1610.neoflex.project.conveyor.constant.MaritalStatus;
import javax.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ScoringDataDTO {
    @NotNull
    @DecimalMin(value = "10000", message = "Сумма кредита должна быть не меньше 10000 рублей.")
    private BigDecimal amount;

    @NotNull
    @Min(value = 6, message = "Срок кредита не должен быть меньше 6 месяцев")
    private Integer term;

    @NotEmpty(message = "Имя - обязательное поле.")
    @Size(min = 2, max = 30, message = "Имя должно иметь длину от 2 до 30 символов.")
    @Pattern(regexp = "[A-Z][a-z]{2,30}", message = "Имя должно состоять из латинских символов.")
    private String firstName;

    @NotEmpty(message = "Фамилия - обязательное поле.")
    @Size(min = 2, max = 30, message = "Фамилия должна иметь длину от 2 до 30 символов.")
    @Pattern(regexp = "[A-Z][a-z]{2,30}", message = "Фамилия должна состоять из латинских символов.")
    private String lastName;

    @Size(min = 2, max = 30, message = "Отчество должно иметь длину от 2 до 30 символов.")
    @Pattern(regexp = "[A-Z][a-z]{2,30}", message = "Отчество должно состоять из латинских символов.")
    private String middleName;

    @NotNull
    private Gender gender;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdate;

    @NotEmpty
    @Pattern(regexp = "\\d{4}", message = "Серия паспорта должна состоять из 4 цифр.")
    private String passportSeries;

    @NotEmpty
    @Pattern(regexp = "\\d{6}", message = "Номер паспорта должен состоять из 6 цифр.")
    private String passportNumber;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate passportIssueDate;

    @NotEmpty
    private String passportIssueBranch;

    @NotNull
    private MaritalStatus maritalStatus;

    private Integer dependentAmount;

    @NotNull
    private EmploymentDTO employment;

    @NotNull
    private String account;

    @NotNull
    private Boolean isInsuranceEnabled;

    @NotNull
    private Boolean isSalaryClient;
}
