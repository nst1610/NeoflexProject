package com.github.nst1610.neoflex.project.dossier.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Passport {
    private String series;
    private String number;
    private String issueBranch;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate issueDate;
}
