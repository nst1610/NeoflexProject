package com.github.nst1610.neoflex.project.dossier.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.github.nst1610.neoflex.project.dossier.model.enums.ApplicationStatus;
import com.github.nst1610.neoflex.project.dossier.model.enums.ChangeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationStatusHistory {
    private ApplicationStatus status;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate time;
    private ChangeType changeType;
}
