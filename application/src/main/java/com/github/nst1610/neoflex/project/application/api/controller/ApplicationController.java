package com.github.nst1610.neoflex.project.application.api.controller;

import com.github.nst1610.neoflex.project.application.api.dto.LoanApplicationRequest;
import com.github.nst1610.neoflex.project.application.api.dto.LoanOffer;
import com.github.nst1610.neoflex.project.application.service.ApplicationService;
import com.github.nst1610.neoflex.project.application.util.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/application")
@RequiredArgsConstructor
@Tag(name = "Application", description = "Микросервис заявка")
public class ApplicationController {
    private final ApplicationService service;

    @Operation(
            description = "Расчет возможных условий кредита",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation =  LoanOffer.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation =  ErrorResponse.class)))
            }
    )
    @PostMapping
    public ResponseEntity<List<LoanOffer>> calculatePossibleOffers(@RequestBody LoanApplicationRequest
                                                                      loanApplicationRequest) {
        return new ResponseEntity<>(service.createLoanOfferForClient(loanApplicationRequest), HttpStatus.OK);
    }

    @Operation(
            description = "Выбор одного из предложений",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation =  ErrorResponse.class)))
            }
    )
    @PutMapping("/offer")
    public ResponseEntity<Void> chooseOffer(@RequestBody LoanOffer loanOffer) {
        service.chooseOffer(loanOffer);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
