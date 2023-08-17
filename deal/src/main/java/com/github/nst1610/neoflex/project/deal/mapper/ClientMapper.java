package com.github.nst1610.neoflex.project.deal.mapper;

import com.github.nst1610.neoflex.project.deal.dto.LoanApplicationRequestDTO;
import com.github.nst1610.neoflex.project.deal.entity.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    Client loanApplicationRequestToClient(LoanApplicationRequestDTO loanApplicationRequest);
}
