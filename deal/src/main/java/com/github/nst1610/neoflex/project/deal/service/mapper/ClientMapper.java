package com.github.nst1610.neoflex.project.deal.service.mapper;

import com.github.nst1610.neoflex.project.deal.api.dto.LoanApplicationRequest;
import com.github.nst1610.neoflex.project.deal.repository.entity.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    Client toClient(LoanApplicationRequest loanApplicationRequest);
}
