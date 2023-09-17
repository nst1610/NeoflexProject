package com.github.nst1610.neoflex.project.deal.service;

import com.github.nst1610.neoflex.project.deal.model.enums.ApplicationStatus;
import com.github.nst1610.neoflex.project.deal.repository.entity.Application;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final ApplicationService applicationService;
    private final DealService dealService;

    public Application getApplicationById(Long applicationId) {
        return applicationService.get(applicationId);
    }

    public Application updateStatusFromDossierMS(Long applicationId) {
        Application application = applicationService.get(applicationId);
        dealService.updateStatusHistory(application, ApplicationStatus.DOCUMENT_CREATED);
        applicationService.save(application);
        return application;
    }

    public List<Application> getAllApplications() {
        return applicationService.getAll();
    }

}
