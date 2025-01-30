package com.Park_Api.utils;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

public class CalculateValuePerTime {

    private static final double FIRST_15_MINUTES = 5.00;
    private static final double FIRST_60_MINUTES = 9.25;
    private static final double EXTRA_CHARGE = 1.75;


    public static BigDecimal CalculateValue(LocalDateTime entryDate, LocalDateTime exitDate){

        BigDecimal value = BigDecimal.ZERO;

        Duration t1 = Duration.between(entryDate, exitDate);
        long minutes = t1.toMinutes();

        if (minutes <= 15){
            return value.add(new BigDecimal(FIRST_15_MINUTES));
        }
        if (minutes == 60) {
            return value.add(new BigDecimal(FIRST_60_MINUTES));
        }

        BigDecimal additionalFee = new BigDecimal(EXTRA_CHARGE);
        long extraPeriods = (minutes - 60)/15;
        BigDecimal extraCharge = additionalFee.multiply(BigDecimal.valueOf(extraPeriods));

        return value.add(new BigDecimal(FIRST_60_MINUTES)).add(extraCharge);
    }
}