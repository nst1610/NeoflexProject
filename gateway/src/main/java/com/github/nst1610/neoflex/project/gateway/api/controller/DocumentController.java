package com.github.nst1610.neoflex.project.gateway.api.controller;

import com.github.nst1610.neoflex.project.gateway.service.DocumentService;
import com.github.nst1610.neoflex.project.gateway.util.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Credit Conveyor", description = "Кредитный конвейер")
@RestController
@RequestMapping("/document")
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentService documentService;

    @Operation(
            description = "Запрос на создание документов",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation =  ErrorResponse.class)))
            }
    )
    @PostMapping("/{applicationId}")
    public ResponseEntity<Void> createDocumentsRequest(@Parameter(description = "Id заявки для формирования документов")
                                                           @PathVariable Long applicationId) {
        documentService.createDocumentsRequest(applicationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            description = "Запрос на подписание документов",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation =  ErrorResponse.class)))
            }
    )
    @PostMapping("/{applicationId}/sign")
    public ResponseEntity<Void> signDocumentsRequest(@Parameter(description = "Id заявки для подписания")
                                                         @PathVariable Long applicationId) {
        documentService.signDocumentsRequest(applicationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            description = "Запрос на проверку ses кода",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation =  ErrorResponse.class)))
            }
    )
    @PostMapping("/{applicationId}/sign/code")
    public ResponseEntity<Void> verifySesCodeRequest(@Parameter(description = "Id заявки для подписания")
                                                         @PathVariable Long applicationId,
                                                     @Parameter(description = "ses код для проверки")
                                                     @RequestBody String sesCode) {
        documentService.verifySesCodeRequest(applicationId, sesCode);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
