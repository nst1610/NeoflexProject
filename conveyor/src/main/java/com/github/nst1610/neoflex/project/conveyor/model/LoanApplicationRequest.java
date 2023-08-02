package com.github.nst1610.neoflex.project.conveyor.model;

import javax.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class LoanApplicationRequest {
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

    @NotEmpty(message = "Поле email не должно быть пустым.")
    @Email(message = "Введен некорректный адрес электронной почты.")
    private String email;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdate;

    @NotEmpty
    @Pattern(regexp = "\\d{4}", message = "Серия паспорта должна состоять из 4 цифр.")
    private String passportSeries;

    @NotEmpty
    @Pattern(regexp = "\\d{6}", message = "Номер паспорта должен состоять из 6 цифр.")
    private String passportNumber;
}
