package com.example.demo.kart.utils;

import com.example.demo.kart.domain.model.Driver;
import com.example.demo.kart.domain.model.Lap;
import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@UtilityClass
public class ObjectCreationUtils {

    public static Lap getaLap(int raceNumber, int driverId, int minutesDuration, int secondsDuration) {
        Date seconds = dateMinutesWithSeconds(minutesDuration, secondsDuration);

        return Lap.builder()
                .number(raceNumber)
                .time(seconds)
                .averageSpeed(seconds)
                .driver(aDriver(driverId))
                .build();
    }

    public static Driver aDriver(int id) {
        return Driver.builder()
                .id(id)
                .name("Driver" + id)
                .build();
    }

    public static Date dateMinutes(int minutesToAdd) {
        return Date.from(Instant.EPOCH.plus(minutesToAdd, ChronoUnit.MINUTES));
    }

    public static Date dateMinutesWithSeconds(int minutesToAdd, long secondsToAdd) {
        return Date.from((Instant.EPOCH.plus(minutesToAdd, ChronoUnit.MINUTES).plusSeconds(secondsToAdd)));
    }
}
