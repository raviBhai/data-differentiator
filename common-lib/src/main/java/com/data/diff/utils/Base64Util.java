package com.data.diff.utils;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class Base64Util {

    public static String encode(String inputString){
        String encodedString = Base64.getEncoder().encodeToString(inputString.getBytes());
        return encodedString;
    }

    public static String decode(String inputString){
        byte[] bytes = Base64.getDecoder().decode(inputString);
        String decodedString = "";
        try {
            decodedString = new String(bytes, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return decodedString;
    }
}
