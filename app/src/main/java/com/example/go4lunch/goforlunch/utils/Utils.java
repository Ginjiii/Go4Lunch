package com.example.go4lunch.goforlunch.utils;

import com.google.common.base.Joiner;

import java.util.List;

public class Utils {

    public static String convertListToString(List<String> listString) {
        return Joiner.on(", ").join(listString);
    }
}