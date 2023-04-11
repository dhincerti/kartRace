package com.example.demo.kart.domain.service;

import com.example.demo.kart.domain.model.Driver;
import com.example.demo.kart.domain.model.DriverResults;
import com.example.demo.kart.domain.model.Lap;
import com.example.demo.kart.domain.model.RaceResults;
import com.example.demo.kart.domain.ports.KartRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KartServiceImp implements KartService {
    private static final Logger LOGGER = LoggerFactory.getLogger(KartServiceImp.class);
    private final KartRepository kartRepository;

    @Override
    public RaceResults getRaceResults() {
        LOGGER.info("Starting race results service");

        List<Lap> laps = kartRepository.findAll();

        RaceResults raceResults = createRaceRults(laps);
        orderResultsByTotalTimeAndLaps(raceResults);
        setDriversPosition(raceResults);

        LOGGER.info("Finished race results service");
        return raceResults;
    }

    private static void setDriversPosition(RaceResults raceResults) {
        for (int i = 0; i < raceResults.getDriversResults().size(); i++) {
            DriverResults driverResults = raceResults.getDriversResults().get(i);
            driverResults.setPosition(i + 1);
        }
    }

    private static RaceResults createRaceRults(List<Lap> laps) {
        List<DriverResults> driversResults = createDriverResults(laps); // TODO: Segregar em novo service?
        Date totalTime = getTotalTime(driversResults);

        return new RaceResults(driversResults, totalTime);
    }

    private static Date getTotalTime(List<DriverResults> driversResults) {
        return new Date(driversResults.stream().mapToLong(value -> value.getTotalTime().getTime()).sum());
    }

    private static List<DriverResults> createDriverResults(List<Lap> laps) {
        HashMap<Driver, List<Lap>> lapsGroupingByDriver = groupLapsByDriver(laps);

        return lapsGroupingByDriver.entrySet().stream()
                .map(KartServiceImp::createDriverResults)
                .collect(Collectors.toList());
    }

    private static HashMap<Driver, List<Lap>> groupLapsByDriver(List<Lap> laps) {
        return laps.stream()
                .collect(Collectors.groupingBy(Lap::getDriver, HashMap::new, Collectors.toCollection(ArrayList::new)));
    }

    private static void orderResultsByTotalTimeAndLaps(RaceResults raceResults) {
        raceResults.getDriversResults()
                .sort(Comparator.comparing(DriverResults::getTotalLaps)
                        .reversed()
                        .thenComparing(DriverResults::getTotalTime));
    }

    private static DriverResults createDriverResults(Map.Entry<Driver, List<Lap>> driverListEntry) {
        Integer totalLaps = driverListEntry.getValue().size();
        Date totalTime = calculateTotalTime(driverListEntry.getValue());
        Date avgTime = calculateAvgTime(totalTime, totalLaps);
        Date bestLapTime = getBestLapTime(driverListEntry);

        DriverResults driverResults = new DriverResults();
        driverResults.setId(driverListEntry.getKey().getId());
        driverResults.setName(driverListEntry.getKey().getName());
        driverResults.setLaps(driverListEntry.getValue());
        driverResults.setTotalLaps(totalLaps);
        driverResults.setTotalTime(totalTime);
        driverResults.setAvgTime(avgTime);
        driverResults.setBestTime(bestLapTime);

        return driverResults;
    }

    private static Date getBestLapTime(Map.Entry<Driver, List<Lap>> driverListEntry) {
        return Collections.min(driverListEntry.getValue(), Comparator.comparing(Lap::getTime)).getTime();
    }

    private static Date calculateAvgTime(Date totalTime, Integer totalLaps) {
        return new Date(totalTime.getTime() / totalLaps);
    }

    private static Date calculateTotalTime(List<Lap> laps) {
        return new Date(laps.stream().mapToLong(value -> value.getTime().getTime()).sum());
    }
}
