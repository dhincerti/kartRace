package com.example.demo.kart.domain.utils;

import lombok.experimental.UtilityClass;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static com.example.demo.kart.domain.utils.Constants.TIME_MINUTES_PATTERN;
import static com.example.demo.kart.domain.utils.Constants.TIME_PATTERN;

@UtilityClass
public class Utils {

    public static Date parseTime(String lapTime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_PATTERN);
        return sdf.parse("00:0" + lapTime);
    }

    public static Date parseAvgTime(String avgTime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_PATTERN);
        return sdf.parse("00:00:" + new DecimalFormat("#0.000").format(Double.valueOf(avgTime.replace(",", "."))));
    }

    public static String formatMillisToMinutes(Date time) throws ParseException {
        DateFormat formatter = new SimpleDateFormat(TIME_MINUTES_PATTERN);
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        return formatter.format(time);
    }
}
