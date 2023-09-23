package com.github.nst1610.neoflex.project.dossier.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.github.nst1610.neoflex.project.dossier.model.enums.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Application {
    private Long applicationId;
    private Client client;
    private Credit credit;
    private ApplicationStatus status;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime creationDate;
    private LoanOffer appliedOffer;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime signDate;
    private String sesCode;
    private List<ApplicationStatusHistory> statusHistory;
}
