package com.github.nst1610.neoflex.project.application.util;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private String Message;
    private LocalDateTime timeStamp;
}
