package com.github.nst1610.neoflex.project.deal.controller;

import com.github.nst1610.neoflex.project.deal.dto.FinishRegistrationRequestDTO;
import com.github.nst1610.neoflex.project.deal.dto.LoanApplicationRequestDTO;
import com.github.nst1610.neoflex.project.deal.dto.LoanOfferDTO;
import com.github.nst1610.neoflex.project.deal.service.DealService;
import com.github.nst1610.neoflex.project.deal.util.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Deal", description = "Микросервис сделка")
@RestController
@RequestMapping("/deal")
@AllArgsConstructor
public class DealController {
    private final DealService dealService;

    @Operation(
            description = "Расчет возможных условий кредита",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation =  LoanOfferDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation =  ErrorResponse.class)))
            }
    )
    @PostMapping("/application")
    public ResponseEntity<List<LoanOfferDTO>> calculatePossibleOffers(@RequestBody LoanApplicationRequestDTO
                                                                                  loanApplicationRequest) {
        return new ResponseEntity<>(dealService.getPossibleLoanOffers(loanApplicationRequest),
                HttpStatus.OK);
    }

    @Operation(
            description = "Выбор одного из предложений",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation =  ErrorResponse.class)))
            }
    )
    @PutMapping("/offer")
    public ResponseEntity<Void> chooseOffer(@RequestBody LoanOfferDTO loanOffer) {
        dealService.chooseOffer(loanOffer);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            description = "Завершение регистрации и полный расчет параметров кредита",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation =  ErrorResponse.class)))
            }
    )
    @PutMapping("/calculate/{applicationId}")
    public ResponseEntity<Void> calculateCreditConditions(@Parameter(description = "Id заявки для расчета") @PathVariable Long applicationId,
                                                          @RequestBody FinishRegistrationRequestDTO
                                                                  finishRegistrationRequest) {
        dealService.calculateCreditConditions(finishRegistrationRequest, applicationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
