package com.hyva.bsfms.bs.bsutil;

import org.apache.commons.lang3.RandomStringUtils;


public class AccessTokenGenerator {


    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public static String randomAlphaNumeric() {
//           int count=60;
//           StringBuilder builder = new StringBuilder();
//
//        while (count-- != 0) {
//
//            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
//
//            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
//
//        }
//
//        return builder.toString();


        int length = 60;
        boolean useLetters = true;
        boolean useNumbers = true;
        String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);
        System.out.println(generatedString);
        return  generatedString;

    }

}
