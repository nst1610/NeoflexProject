package com.github.nst1610.neoflex.project.deal.model;

import com.github.nst1610.neoflex.project.deal.model.enums.Theme;
import lombok.Builder;

@Builder
public class EmailMessage {
    private String address;
    private Theme theme;
    private Long applicationId;
}
