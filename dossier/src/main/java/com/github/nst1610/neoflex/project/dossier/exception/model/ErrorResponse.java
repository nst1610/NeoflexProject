package com.github.nst1610.neoflex.project.dossier.exception.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private String Message;
    private long timeStamp;
}
