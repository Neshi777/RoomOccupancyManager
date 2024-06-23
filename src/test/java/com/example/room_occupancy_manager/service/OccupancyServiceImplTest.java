package com.example.room_occupancy_manager.service;

import com.example.room_occupancy_manager.data_loader.JsonDataLoader;
import com.example.room_occupancy_manager.model.OccupancyRequest;
import com.example.room_occupancy_manager.model.OccupancyResponse;
import com.example.room_occupancy_manager.model.PotentialOccupancies;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class OccupancyServiceImplTest {

    @Mock
    private JsonDataLoader jsonDataLoader;

    @InjectMocks
    private OccupancyServiceImpl occupancyService;

    @BeforeEach
    void setUp() {

        // Prepare mock data
        List<Double> offers = Arrays.asList(23.0, 45.0, 155.0, 374.0, 22.0, 99.99, 100.0, 101.0, 115.0, 209.0);
        PotentialOccupancies potentialOccupancies = new PotentialOccupancies();
        potentialOccupancies.setOffers(offers);

        // Mock the behavior of jsonUtil
        lenient().when(jsonDataLoader.loadPotentialOccupanciesFromFile(null)).thenReturn(potentialOccupancies);

    }

    @Test
    void testCalculateOccupancy_scenario1() {
        // Given
        int freePremiumRooms = 3;
        int freeEconomyRooms = 3;
        OccupancyRequest request = new OccupancyRequest(freePremiumRooms, freeEconomyRooms);

        // When
        OccupancyResponse response = occupancyService.calculateOccupancy(request);

        // Then
        assertEquals(3, response.getUsagePremium());
        assertEquals(738.0, response.getRevenuePremium(), 0.01);
        assertEquals(3, response.getUsageEconomy());
        assertEquals(167.99, response.getRevenueEconomy(), 0.01);
    }

    @Test
    void testCalculateOccupancy_scenario2() {
        // Given
        int freePremiumRooms = 7;
        int freeEconomyRooms = 5;
        OccupancyRequest request = new OccupancyRequest(freePremiumRooms, freeEconomyRooms);

        // When
        OccupancyResponse response = occupancyService.calculateOccupancy(request);

        // Then
        assertEquals(6, response.getUsagePremium());
        assertEquals(1054.0, response.getRevenuePremium(), 0.01);
        assertEquals(4, response.getUsageEconomy());
        assertEquals(189.99, response.getRevenueEconomy(), 0.01);
    }

    @Test
    void testCalculateOccupancy_scenario3() {
        // Given
        int freePremiumRooms = 2;
        int freeEconomyRooms = 7;
        OccupancyRequest request = new OccupancyRequest(freePremiumRooms, freeEconomyRooms);

        // When
        OccupancyResponse response = occupancyService.calculateOccupancy(request);

        // Then
        assertEquals(2, response.getUsagePremium());
        assertEquals(583.0, response.getRevenuePremium(), 0.01);
        assertEquals(4, response.getUsageEconomy());
        assertEquals(189.99, response.getRevenueEconomy(), 0.01);
    }

    @Test
    void testCalculateOccupancy_scenario4() {
        // Given
        int freePremiumRooms = 7;
        int freeEconomyRooms = 1;
        OccupancyRequest request = new OccupancyRequest(freePremiumRooms, freeEconomyRooms);

        // When
        OccupancyResponse response = occupancyService.calculateOccupancy(request);

        // Then
        assertEquals(7, response.getUsagePremium());
        assertEquals(1153.99, response.getRevenuePremium(), 0.01);
        assertEquals(1, response.getUsageEconomy());
        assertEquals(45.00, response.getRevenueEconomy(), 0.01);
    }

    @Test
    void testCalculateOccupancy_negativePremiumRooms() {
        // Given
        int freePremiumRooms = -1;
        int freeEconomyRooms = 3;
        OccupancyRequest request = new OccupancyRequest(freePremiumRooms, freeEconomyRooms);

        // When & Then
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> occupancyService.calculateOccupancy(request));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Number of rooms cannot be negative", exception.getReason());
    }


    @Test
    void testCalculateOccupancy_negativeEconomyRooms() {
        // Given
        int freePremiumRooms = 3;
        int freeEconomyRooms = -1;
        OccupancyRequest request = new OccupancyRequest(freePremiumRooms, freeEconomyRooms);

        // When & Then
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> occupancyService.calculateOccupancy(request));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Number of rooms cannot be negative", exception.getReason());
    }

}
