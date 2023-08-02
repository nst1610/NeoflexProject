package com.github.nst1610.neoflex.project.conveyor.mapper;

import com.github.nst1610.neoflex.project.conveyor.dto.CreditDTO;
import com.github.nst1610.neoflex.project.conveyor.model.Credit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreditMapper {
    Credit toModel(CreditDTO dto);
    CreditDTO toDTO(Credit model);
}
