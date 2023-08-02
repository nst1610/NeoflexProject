package com.github.nst1610.neoflex.project.conveyor.controller;

import com.github.nst1610.neoflex.project.conveyor.dto.CreditDTO;
import com.github.nst1610.neoflex.project.conveyor.dto.LoanApplicationRequestDTO;
import com.github.nst1610.neoflex.project.conveyor.dto.LoanOfferDTO;
import com.github.nst1610.neoflex.project.conveyor.dto.ScoringDataDTO;
import com.github.nst1610.neoflex.project.conveyor.mapper.CreditMapper;
import com.github.nst1610.neoflex.project.conveyor.mapper.LoanApplicationRequestMapper;
import com.github.nst1610.neoflex.project.conveyor.mapper.LoanOfferMapper;
import com.github.nst1610.neoflex.project.conveyor.mapper.ScoringDataMapper;
import com.github.nst1610.neoflex.project.conveyor.model.LoanApplicationRequest;
import com.github.nst1610.neoflex.project.conveyor.service.CreditService;
import com.github.nst1610.neoflex.project.conveyor.service.LoanOfferService;
import com.github.nst1610.neoflex.project.conveyor.util.DataException;
import com.github.nst1610.neoflex.project.conveyor.util.ErrorResponse;
import com.github.nst1610.neoflex.project.conveyor.util.LoanApplicationRequestValidator;
import com.github.nst1610.neoflex.project.conveyor.util.ScoringException;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conveyor")
@AllArgsConstructor
public class ConveyorController {
    private final LoanOfferService loanOfferService;
    private final CreditService creditService;
    private final LoanOfferMapper loanOfferMapper;
    private final LoanApplicationRequestMapper loanApplicationRequestMapper;
    private final CreditMapper creditMapper;
    private final ScoringDataMapper scoringDataMapper;
    private final LoanApplicationRequestValidator validator;

    @PostMapping("/offers")
    public ResponseEntity<List<LoanOfferDTO>> possibleOffersForClient(
            @RequestBody @Valid LoanApplicationRequestDTO loanApplicationRequestDTO,
            BindingResult bindingResult
    ){
        LoanApplicationRequest loanApplicationRequest = loanApplicationRequestMapper.toModel(loanApplicationRequestDTO);
        validator.validate(loanApplicationRequest, bindingResult);

        if(bindingResult.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors)
                errorMsg.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append("; ");

            throw new DataException(errorMsg.toString());
        }

        List<LoanOfferDTO> response = loanOfferMapper.toDTOList(loanOfferService
                .createLoanOfferForClient(loanApplicationRequestMapper
                        .toModel(loanApplicationRequestDTO)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/calculation")
    public ResponseEntity<CreditDTO> calculatedCreditConditions(
            @RequestBody @Valid ScoringDataDTO scoringDataDTO,
            BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors)
                errorMsg.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append("; ");

            throw new DataException(errorMsg.toString());
        }

        CreditDTO response = creditMapper.toDTO(creditService.getCalculatedCreditOffer(
                scoringDataMapper.toModel(scoringDataDTO)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(ScoringException scoringException){
        ErrorResponse response = new ErrorResponse(scoringException.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(DataException scoringException){
        ErrorResponse response = new ErrorResponse(scoringException.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
