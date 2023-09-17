package com.github.nst1610.neoflex.project.deal.api.controller;

import com.github.nst1610.neoflex.project.deal.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/deal/document")
public class DocumentController {
    private final DocumentService documentService;

    @PutMapping("/{applicationId}/send")
    public ResponseEntity<Void> sendDocuments(@PathVariable Long applicationId) {
        documentService.sendDocuments(applicationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{applicationId}/sign")
    public ResponseEntity<Void> signDocumentsRequest(@PathVariable Long applicationId) {
        documentService.signDocumentsRequest(applicationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{applicationId}/code")
    public ResponseEntity<Void> verifySesCode(@PathVariable Long applicationId,
                                                        @RequestBody String sesCode) {
        documentService.sendCreditIssuedRequest(applicationId, sesCode);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
