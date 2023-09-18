package com.github.nst1610.neoflex.project.gateway.api.controller;

import com.github.nst1610.neoflex.project.gateway.api.dto.FinishRegistrationRequest;
import com.github.nst1610.neoflex.project.gateway.api.dto.LoanApplicationRequest;
import com.github.nst1610.neoflex.project.gateway.api.dto.LoanOffer;
import com.github.nst1610.neoflex.project.gateway.service.ApplicationService;
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

import java.util.List;

@Tag(name = "Credit Conveyor", description = "Кредитный конвейер")
@RestController
@RequestMapping("/application")
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;

    @Operation(
            description = "Расчет возможных условий кредита",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation =  LoanOffer.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation =  ErrorResponse.class)))
            }
    )
    @PostMapping
    public ResponseEntity<List<LoanOffer>> createLoanApplication(@RequestBody LoanApplicationRequest
                                                                             loanApplicationRequest) {
        return new ResponseEntity<>(applicationService.createLoanOfferForClient(loanApplicationRequest), HttpStatus.OK);
    }

    @Operation(
            description = "Выбор одного из предложений",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation =  ErrorResponse.class)))
            }
    )
    @PostMapping("/offer")
    public ResponseEntity<Void> chooseOffer(@RequestBody LoanOffer loanOffer) {
        applicationService.chooseOffer(loanOffer);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            description = "Завершение регистрации и полный расчет параметров кредита",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation =  ErrorResponse.class)))
            }
    )
    @PostMapping("/registration/{applicationId}")
    public ResponseEntity<Void> finishRegistration(@Parameter(description = "Id заявки для расчета") @PathVariable Long applicationId,
                                                   @RequestBody FinishRegistrationRequest finishRegistrationRequest) {
        applicationService.finishRegistration(applicationId, finishRegistrationRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
