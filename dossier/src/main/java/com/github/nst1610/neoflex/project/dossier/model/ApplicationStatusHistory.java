package com.github.nst1610.neoflex.project.dossier.model;

import com.github.nst1610.neoflex.project.dossier.model.enums.ApplicationStatus;
import com.github.nst1610.neoflex.project.dossier.model.enums.ChangeType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ApplicationStatusHistory {
    private ApplicationStatus status;
    private LocalDate time;
    private ChangeType changeType;
}
