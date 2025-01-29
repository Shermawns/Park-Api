package com.Park_Api.utils;

import java.time.LocalDateTime;

public class GarageUtils {

    public static String generateReceipt(){
        LocalDateTime localDateTime = LocalDateTime.now();

        String receipt = localDateTime.toString().substring(0,19);

        return receipt.replace("-","")
                .replace(":","")
                .replace("T","-");
    }
}
