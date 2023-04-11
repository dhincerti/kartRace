package com.example.demo.kart.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lap {
    private Driver driver;
    private Integer number;
    private Date time;
    private Date averageSpeed;
}
