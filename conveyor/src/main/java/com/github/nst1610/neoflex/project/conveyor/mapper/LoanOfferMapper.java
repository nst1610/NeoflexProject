package com.github.nst1610.neoflex.project.conveyor.mapper;

import com.github.nst1610.neoflex.project.conveyor.dto.LoanOfferDTO;
import com.github.nst1610.neoflex.project.conveyor.model.LoanOffer;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LoanOfferMapper {
    LoanOffer toModel(LoanOfferDTO dto);
    LoanOfferDTO toDTO(LoanOffer model);
    List<LoanOffer> toModelList(List<LoanOfferDTO> dtos);
    List<LoanOfferDTO> toDTOList(List<LoanOffer> models);
}
