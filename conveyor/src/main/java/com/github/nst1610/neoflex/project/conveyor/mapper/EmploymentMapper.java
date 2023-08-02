package com.github.nst1610.neoflex.project.conveyor.mapper;

import com.github.nst1610.neoflex.project.conveyor.dto.EmploymentDTO;
import com.github.nst1610.neoflex.project.conveyor.model.Employment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmploymentMapper {
    Employment toModel(EmploymentDTO dto);
    EmploymentDTO toDTO(Employment model);
}
