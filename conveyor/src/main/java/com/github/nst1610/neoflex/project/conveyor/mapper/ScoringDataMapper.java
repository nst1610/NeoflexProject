package com.github.nst1610.neoflex.project.conveyor.mapper;

import com.github.nst1610.neoflex.project.conveyor.dto.ScoringDataDTO;
import com.github.nst1610.neoflex.project.conveyor.model.ScoringData;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = EmploymentMapper.class)
public interface ScoringDataMapper {
    ScoringData toModel(ScoringDataDTO dto);
    ScoringDataDTO toDTO(ScoringData model);
}
