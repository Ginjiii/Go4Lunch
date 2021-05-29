package com.example.go4lunch.goforlunch.utils;

import com.example.go4lunch.goforlunch.models.details.OpeningHours;
import com.example.go4lunch.goforlunch.models.details.OpeningPeriods;
import com.go4lunch.R;
import com.google.common.base.Joiner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Utils {

    public static String convertListToString(List<String> listString) {
        return Joiner.on(", ").join(listString);
    }

    public static Date convertStringToDate(int hour) {
        String hourInString = String.valueOf(hour);
        DateFormat dateFormat = new SimpleDateFormat("HHmm", Locale.FRANCE);
        try {
            return dateFormat.parse(hourInString);
        } catch (ParseException e) {
            return null;
        }
    }

    public static int getOpeningTime(OpeningHours openingHours) {
        if (openingHours == null || openingHours.getOpeningPeriods() == null)
            return R.string.no_time;
        if (openingHours.getOpenNow() != null && !openingHours.getOpenNow()) {
            return R.string.closed;
        }
        int dayOfTheWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
        if (openingHours.getOpeningPeriods().size() >= dayOfTheWeek + 1) {
            OpeningPeriods periodOfTheDay = openingHours.getOpeningPeriods().get(dayOfTheWeek);

            if (periodOfTheDay.getCloseHour() == null) return R.string.open_24_7;

            String closureString = periodOfTheDay.getCloseHour().getTime();
            int closure = Integer.parseInt(closureString);

            Date todayDate = Calendar.getInstance().getTime();
            DateFormat dateFormat = new SimpleDateFormat("HHmm", Locale.FRANCE);
            String todayDateString = dateFormat.format(todayDate);
            int timeNow = Integer.parseInt(todayDateString);
            int timeBeforeClosure = closure - timeNow;
            if (timeBeforeClosure <= 100) {
                return R.string.closing_soon;
            } else {
                return closure;
            }
        }
        return R.string.no_time;
    }
}