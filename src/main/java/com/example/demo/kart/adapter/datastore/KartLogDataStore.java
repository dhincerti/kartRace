package com.example.demo.kart.adapter.datastore;

import com.example.demo.kart.domain.model.Driver;
import com.example.demo.kart.domain.model.Lap;
import com.example.demo.kart.domain.ports.KartDataStore;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import static com.example.demo.kart.domain.utils.Constants.SPLIT_DELIMITER;
import static com.example.demo.kart.domain.utils.Utils.parseAvgTime;
import static com.example.demo.kart.domain.utils.Utils.parseTime;

@Component
@Profile("log")
@RequiredArgsConstructor
public class KartLogDataStore implements KartDataStore {
    private static final Logger LOGGER = LoggerFactory.getLogger(KartLogDataStore.class);

    @Value("${kart.file}")
    private String fileLocation;

    private List<Lap> laps = new ArrayList<>();

    @PostConstruct
    public void loadKartData() {
        LOGGER.info("Starting to load kart data");

        this.laps = loadFromLogFile(fileLocation);

        LOGGER.info("Loaded {} laps from {} file.", laps.size(), fileLocation);
    }

    @Override
    public List<Lap> getLaps() {
        return laps;
    }

    public List<Lap> loadFromLogFile(String filePath) {
        List<Lap> laps = new ArrayList<>();

        try (Scanner scanner = new Scanner(ResourceUtils.getFile(filePath))) {
            while (scanner.hasNextLine()) {
                laps.add(parseLap(scanner.nextLine().trim()));
            }
        } catch (ParseException | FileNotFoundException e) {
            LOGGER.error("Error while loading data.", e);
            throw new RuntimeException(e);
        }

        return laps;
    }

    private Lap parseLap(String line) throws ParseException {
        String[] lapInfo = line.split(SPLIT_DELIMITER);

        Integer driverId = Integer.valueOf(lapInfo[1]);
        String driverName = lapInfo[3];
        Integer lapNumber = Integer.valueOf(lapInfo[4]);
        Date lapTime = parseTime(lapInfo[5]);
        Date lapAverageSpeed = parseAvgTime(lapInfo[6]);

        Driver driver = new Driver(driverId, driverName);
        return new Lap(driver, lapNumber, lapTime, lapAverageSpeed);

    }
}
