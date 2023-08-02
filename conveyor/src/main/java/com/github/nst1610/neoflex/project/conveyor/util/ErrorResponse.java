package com.github.nst1610.neoflex.project.conveyor.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private String Message;
    private long timeStamp;
}
