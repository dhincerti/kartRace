package com.example.demo.kart.application.controllers;

import com.example.demo.kart.domain.model.DriverResults;
import com.example.demo.kart.domain.model.RaceResults;
import com.example.demo.kart.domain.service.KartService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

import static com.example.demo.kart.domain.utils.Utils.formatMillisToMinutes;

@RestController
@RequiredArgsConstructor
public class RaceController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RaceController.class);

    private final KartService kartService;

    @GetMapping(path = "/kart/races/results", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getRaceResults() {
        LOGGER.info("Request Received");

        RaceResults raceResults = kartService.getRaceResults();
        String textResponse = buildTextResponse(raceResults);

        LOGGER.info(textResponse);
        return ResponseEntity.ok(textResponse);
    }

    private static String buildTextResponse(RaceResults raceResults) {
        String response = "Race results:\n";

        for (DriverResults driver : raceResults.getDriversResults()) {
            try {
                response = response.concat("---------------------------------------").concat("\n")
                        .concat(driver.getId().toString()).concat(" - ").concat(driver.getName()).concat("\n")
                        .concat("Laps: ").concat(String.valueOf(driver.getLaps().size())).concat("\n")
                        .concat("Position: ").concat(driver.getPosition().toString()).concat("\n")
                        .concat("Total time: ").concat(formatMillisToMinutes(driver.getTotalTime())).concat("\n")
                        .concat("Avg time: ").concat(formatMillisToMinutes(driver.getAvgTime())).concat("\n")
                        .concat("Best time: ").concat(formatMillisToMinutes(driver.getBestTime())).concat("\n");
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

        return response;
    }
}
