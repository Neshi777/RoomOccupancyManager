package com.example.room_occupancy_manager.data_loader;

import com.example.room_occupancy_manager.model.PotentialOccupancies;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JsonDataLoader {

    private final ObjectMapper objectMapper;
    private final ResourceLoader resourceLoader;

    public PotentialOccupancies loadPotentialOccupanciesFromFile(String filePath) {
        try {
            Resource resource = resourceLoader.getResource(filePath);
            return objectMapper.readValue(resource.getInputStream(), PotentialOccupancies.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load potential occupancies from file", e);
        }
    }
}