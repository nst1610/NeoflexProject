package com.github.nst1610.neoflex.project.gateway.api.controller;

import com.github.nst1610.neoflex.project.gateway.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/document")
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentService documentService;

    @PostMapping("/{applicationId}")
    public ResponseEntity<Void> createDocumentsRequest(@PathVariable Long applicationId) {
        documentService.createDocumentsRequest(applicationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{applicationId}/sign")
    public ResponseEntity<Void> signDocumentsRequest(@PathVariable Long applicationId) {
        documentService.signDocumentsRequest(applicationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{applicationId}/sign/code")
    public ResponseEntity<Void> verifySesCodeRequest(@PathVariable Long applicationId,
                                                     @RequestBody String sesCode) {
        documentService.verifySesCodeRequest(applicationId, sesCode);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
