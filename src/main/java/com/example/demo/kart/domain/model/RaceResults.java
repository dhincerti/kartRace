package com.example.demo.kart.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RaceResults {
    private List<DriverResults> driversResults = new ArrayList<>();
    private Date totalTime;
}
