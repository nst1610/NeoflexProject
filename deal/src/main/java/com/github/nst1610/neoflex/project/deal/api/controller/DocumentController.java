package com.github.nst1610.neoflex.project.deal.api.controller;

import com.github.nst1610.neoflex.project.deal.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/deal/document")
public class DocumentController {
    private final DocumentService documentService;

    @PostMapping("/{applicationId}/send")
    public ResponseEntity<Void> sendDocuments(@PathVariable Long applicationId) {
        documentService.sendDocuments(applicationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{applicationId}/sign")
    public ResponseEntity<Void> signDocuments(@PathVariable Long applicationId) {
        documentService.signDocuments(applicationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
