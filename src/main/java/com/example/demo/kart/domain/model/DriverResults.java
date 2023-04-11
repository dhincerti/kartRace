package com.example.demo.kart.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class DriverResults {
    private Integer id;
    private String name;
    private Integer position;
    private Date totalTime;
    private Integer totalLaps;
    private Date avgTime;
    private Date bestTime;
    private Date difference;
    private List<Lap> laps = new ArrayList<>();
}
