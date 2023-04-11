package com.example.demo.kart.adapter.repository;

import com.example.demo.kart.domain.model.Lap;
import com.example.demo.kart.domain.ports.KartDataStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class KartRepository implements com.example.demo.kart.domain.ports.KartRepository {
    private final KartDataStore dataStore;

    @Override
    public List<Lap> findAll() {
        return dataStore.getLaps();
    }
}
