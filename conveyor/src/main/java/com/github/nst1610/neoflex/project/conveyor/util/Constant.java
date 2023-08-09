package com.github.nst1610.neoflex.project.conveyor.util;

import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;

public class Constant {
    public static final BigDecimal INSURANCE_PERCENT_COST = BigDecimal.valueOf(8);
    public static final BigDecimal MONTHS_IN_YEAR = BigDecimal.valueOf(12);
    public static final Integer MIN_AGE_FOR_MALE = 30;
    public static final Integer MAX_AGE_FOR_MALE = 55;
    public static final Integer MIN_AGE_FOR_FEMALE = 35;
    public static final Integer MAX_AGE_FOR_FEMALE = 60;
    public static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);
    public static final Integer MIN_CLIENT_AGE = 20;
    public static final Integer MAX_CLIENT_AGE = 60;
    public static final Integer MAX_SALARY_COUNT = 20;
    public static final Integer MIN_TOTAL_WORK_EXPERIENCE = 12;
    public static final Integer MIN_CURRENT_WORK_EXPERIENCE = 3;
    public static final BigDecimal MIN_AMOUNT =BigDecimal.valueOf(50000);
    public static final Integer MIN_TERM = 6;

}
