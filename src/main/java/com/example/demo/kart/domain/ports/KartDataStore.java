package com.example.demo.kart.domain.ports;

import com.example.demo.kart.domain.model.Lap;

import java.util.List;

public interface KartDataStore {
    void loadKartData();
    List<Lap> getLaps();
}
