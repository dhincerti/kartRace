package com.example.demo.kart.domain.service;

import com.example.demo.kart.domain.model.Lap;
import com.example.demo.kart.domain.model.RaceResults;
import com.example.demo.kart.domain.ports.KartRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.example.demo.kart.utils.ObjectCreationUtils.dateMinutesWithSeconds;
import static com.example.demo.kart.utils.ObjectCreationUtils.getaLap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class KartServiceImpTest {
    private final KartRepository kartRepository = mock(KartRepository.class);

    private final KartService kartService = new KartServiceImp(kartRepository);

    @Test
    void shouldBuildRaceResults() {
        Lap aLapOfOneDriver = getaLap(1, 1, 1, 10);
        Lap anotherLapOfOneDriver = getaLap(1, 1, 1, 12);
        Lap aLapOfAnetherDriver = getaLap(2, 2, 1, 15);
        List<Lap> laps = List.of(aLapOfOneDriver, anotherLapOfOneDriver, aLapOfAnetherDriver);

        when(kartRepository.findAll()).thenReturn(laps);

        RaceResults raceResults = kartService.getRaceResults();

        assertThat(raceResults.getDriversResults(), hasSize(2));
        assertThat(raceResults.getTotalTime().getTime(), equalTo(dateMinutesWithSeconds(3, 37).getTime()));
    }
}