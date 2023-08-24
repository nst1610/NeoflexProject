package com.github.nst1610.neoflex.project.deal.service.mapper;

import com.github.nst1610.neoflex.project.deal.api.dto.CreditDTO;
import com.github.nst1610.neoflex.project.deal.repository.entity.Credit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreditMapper {
    Credit toCredit(CreditDTO creditDTO);
}
