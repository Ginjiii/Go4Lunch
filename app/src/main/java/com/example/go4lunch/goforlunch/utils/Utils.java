package com.example.go4lunch.goforlunch.utils;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.base.Joiner;

import java.util.List;

public class Utils {

    public static String convertListToString(List<String> listString) {
        return Joiner.on(", ").join(listString);
    }
}

