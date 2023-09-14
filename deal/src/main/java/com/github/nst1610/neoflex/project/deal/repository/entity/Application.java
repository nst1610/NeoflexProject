package com.github.nst1610.neoflex.project.deal.repository.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.github.nst1610.neoflex.project.deal.api.dto.LoanOffer;
import com.github.nst1610.neoflex.project.deal.model.ApplicationStatusHistory;
import com.github.nst1610.neoflex.project.deal.model.enums.ApplicationStatus;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "application")
public class Application {
    @Id
    @Column(name = "application_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationId;

    @OneToOne
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    @JsonManagedReference
    private Client client;

    @OneToOne
    @JoinColumn(name = "credit_id", referencedColumnName = "credit_id")
    @JsonManagedReference
    private Credit credit;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Type(type = "io.hypersistence.utils.hibernate.type.json.JsonBinaryType")
    @Column(name = "applied_offer")
    private LoanOffer appliedOffer;

    @Column(name = "sign_date")
    private LocalDateTime signDate;

    @Column(name = "ses_code")
    private String sesCode;

    @Type(type = "io.hypersistence.utils.hibernate.type.json.JsonBinaryType")
    @Column(name = "status_history")
    private List<ApplicationStatusHistory> statusHistory;
}
