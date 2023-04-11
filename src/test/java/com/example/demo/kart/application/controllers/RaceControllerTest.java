package com.example.demo.kart.application.controllers;

import com.example.demo.kart.domain.model.RaceResults;
import com.example.demo.kart.domain.service.KartService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RaceControllerTest {
    private final KartService kartService = mock(KartService.class);

    private final RaceController raceController = new RaceController(kartService);

    @Test
    void shouldBuildRaceResults() {
        RaceResults aRaceResult = new RaceResults();

        when(kartService.getRaceResults()).thenReturn(aRaceResult);

        ResponseEntity<String> raceResults = raceController.getRaceResults();

        assertThat(raceResults.getStatusCode(), is(HttpStatus.OK));
        assertThat(raceResults.getBody(), not((emptyOrNullString())));
    }

}