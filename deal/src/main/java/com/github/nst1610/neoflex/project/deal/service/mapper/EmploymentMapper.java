package com.github.nst1610.neoflex.project.deal.service.mapper;

import com.github.nst1610.neoflex.project.deal.api.dto.EmploymentDTO;
import com.github.nst1610.neoflex.project.deal.model.Employment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmploymentMapper {
    Employment toEmployment(EmploymentDTO employmentDTO);
    EmploymentDTO toEmploymentDto(Employment employment);
}
