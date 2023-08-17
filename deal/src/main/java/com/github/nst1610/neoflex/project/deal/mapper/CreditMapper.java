package com.github.nst1610.neoflex.project.deal.mapper;

import com.github.nst1610.neoflex.project.deal.dto.CreditDTO;
import com.github.nst1610.neoflex.project.deal.entity.Credit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreditMapper {
    Credit toModel(CreditDTO creditDTO);
}
