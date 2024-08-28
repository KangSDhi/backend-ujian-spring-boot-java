package dev.kangsdhi.backendujianspringbootjava.utils;

import java.security.SecureRandom;

public class GenerateUtils {

    private final String ALPHA_NUMERIS_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final int THE_SIX = 6;

    public String generatedSixDigitRandomStringNumeric(){
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < THE_SIX; i++) {
            int index = secureRandom.nextInt(ALPHA_NUMERIS_STRING.length());
            char randomChar = ALPHA_NUMERIS_STRING.charAt(index);
            stringBuilder.append(randomChar);
        }

        return stringBuilder.toString();
    }
}
