package com.github.nst1610.neoflex.project.conveyor.mapper;

import com.github.nst1610.neoflex.project.conveyor.dto.LoanApplicationRequestDTO;
import com.github.nst1610.neoflex.project.conveyor.model.LoanApplicationRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanApplicationRequestMapper {
    LoanApplicationRequestDTO toDTO(LoanApplicationRequest model);
    LoanApplicationRequest toModel(LoanApplicationRequestDTO dto);

}
