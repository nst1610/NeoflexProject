package com.github.nst1610.neoflex.project.deal.entity;

import com.github.nst1610.neoflex.project.deal.dto.LoanOfferDTO;
import com.github.nst1610.neoflex.project.deal.model.ApplicationStatusHistory;
import com.github.nst1610.neoflex.project.deal.model.enums.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "application")
public class Application {
    @Id
    @Column(name = "application_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationId;

    @OneToOne
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    private Client client;

    @OneToOne
    @JoinColumn(name = "credit_id", referencedColumnName = "credit_id")
    private Credit credit;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Type(type = "io.hypersistence.utils.hibernate.type.json.JsonBinaryType")
    @Column(name = "applied_offer")
    private LoanOfferDTO appliedOffer;

    @Column(name = "sign_date")
    private LocalDate signDate;

    @Column(name = "ses_code")
    private String sesCode;

    @Type(type = "io.hypersistence.utils.hibernate.type.json.JsonBinaryType")
    @Column(name = "status_history")
    private List<ApplicationStatusHistory> statusHistory;
}
