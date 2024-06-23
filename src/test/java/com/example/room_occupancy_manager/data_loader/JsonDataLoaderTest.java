package com.example.room_occupancy_manager.data_loader;

import com.example.room_occupancy_manager.model.PotentialOccupancies;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class JsonDataLoaderTest {

    @Autowired
    private JsonDataLoader jsonDataLoader;

    @Autowired
    private Environment environment;


    @Test
    public void testShouldLoadDataFromJsonFile() {
        String filePath = environment.getProperty("potential.occupancies.file");
        List<Double> expectedDataLoad = Arrays.asList(23.0, 45.0, 155.0, 374.0, 22.0, 99.99, 100.0, 101.0, 115.0, 209.0);
        assertNotNull(filePath);

        PotentialOccupancies potentialOccupancies = jsonDataLoader.loadPotentialOccupanciesFromFile(filePath);

        assertNotNull(potentialOccupancies);
        assertEquals(expectedDataLoad, potentialOccupancies.getOffers());
    }
}
