package com.github.nst1610.neoflex.project.deal.mapper;

import com.github.nst1610.neoflex.project.deal.dto.EmploymentDTO;
import com.github.nst1610.neoflex.project.deal.model.Employment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmploymentMapper {
    Employment toModel(EmploymentDTO employmentDTO);
    EmploymentDTO toDto(Employment employment);
}
