package com.github.nst1610.neoflex.project.deal.api.controller.admin;

import com.github.nst1610.neoflex.project.deal.repository.entity.Application;
import com.github.nst1610.neoflex.project.deal.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/deal/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/application/{applicationId}")
    public ResponseEntity<Application> getApplicationById(@PathVariable Long applicationId) {
        return new ResponseEntity<>(adminService.getApplicationById(applicationId), HttpStatus.OK);
    }

    @PutMapping("/application/{applicationId}/status")
    public ResponseEntity<Application> updateApplicationStatus(@PathVariable Long applicationId) {
        return new ResponseEntity<>(adminService.updateStatusFromDossierMS(applicationId), HttpStatus.OK);
    }
}
