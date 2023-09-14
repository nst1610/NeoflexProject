package com.github.nst1610.neoflex.project.dossier.model;

import com.github.nst1610.neoflex.project.dossier.model.enums.Theme;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailMessage {
    private String address;
    private Theme theme;
    private Long applicationId;
}
