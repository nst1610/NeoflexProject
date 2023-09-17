package com.github.nst1610.neoflex.project.dossier.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.github.nst1610.neoflex.project.dossier.model.enums.Gender;
import com.github.nst1610.neoflex.project.dossier.model.enums.MaritalStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    private long clientId;
    private String lastName;
    private String firstName;
    private String middleName;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate birthDate;
    private String email;
    private Gender gender;
    private MaritalStatus maritalStatus;
    private Integer dependentAmount;
    private Passport passport;
    private Employment employment;
    private String account;
}
