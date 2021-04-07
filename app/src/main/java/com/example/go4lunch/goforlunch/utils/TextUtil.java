package com.example.go4lunch.goforlunch.utils;

import com.google.common.base.Joiner;

import java.util.List;

public abstract class TextUtil {

    public static boolean isEmailCorrect(String email){
        return isTextContainsAt(email) && isTextContainsDomainExtension(email);

    }

    public static boolean isTextLongEnough(String text, int minSize){
        return text.length() >= minSize;

    }

    public static String convertListToString(List<String> listString){
        return Joiner.on(", ").join(listString);

    }

    private static boolean isTextContainsAt(String text){
        return text.contains("@");
    }

    private static boolean isTextContainsDomainExtension(String text){
        String[] emailPart = text.split("@");
        if (emailPart.length > 1) {
            String[] domain = emailPart[1].split("\\.");
            return domain.length > 1;
        }
        return false;
    }
}
