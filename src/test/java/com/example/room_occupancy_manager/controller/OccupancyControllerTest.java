package com.example.room_occupancy_manager.controller;

import com.example.room_occupancy_manager.model.OccupancyRequest;
import com.example.room_occupancy_manager.model.OccupancyResponse;
import com.example.room_occupancy_manager.service.OccupancyServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(OccupancyController.class)
@ContextConfiguration(classes = {OccupancyController.class, OccupancyServiceImpl.class})
public class OccupancyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OccupancyServiceImpl occupancyService;

    @Test
    public void whenValidRequest_thenReturns200() throws Exception {
        // Given
        OccupancyRequest request = new OccupancyRequest(5, 10);

        // Mock response
        OccupancyResponse response = new OccupancyResponse();
        response.setUsagePremium(3);
        response.setRevenuePremium(738.0);
        response.setUsageEconomy(3);
        response.setRevenueEconomy(167.99);

        when(occupancyService.calculateOccupancy(ArgumentMatchers.any(OccupancyRequest.class)))
                .thenReturn(response);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/calculate-occupancy")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    public void whenEmptyRequestBody_thenReturns400() throws Exception {
        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/calculate-occupancy")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof HttpMessageNotReadableException))
                .andExpect(result -> assertTrue(Objects.requireNonNull(result.getResolvedException()).getMessage().contains("Required request body is missing")));
    }

    @Test
    public void whenNegativeFreePremiumRooms_thenReturns400() throws Exception {
        // Given
        OccupancyRequest request = new OccupancyRequest(-1, 10);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/calculate-occupancy")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> {
                    MethodArgumentNotValidException ex = (MethodArgumentNotValidException) result.getResolvedException();
                    assert ex != null;
                    String message = Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage();
                    assertEquals("Number of free Premium rooms cannot be negative", message);
                });
    }

    @Test
    public void whenNegativeFreeEconomyRooms_thenReturns400() throws Exception {
        // Given
        OccupancyRequest request = new OccupancyRequest(10, -1);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/calculate-occupancy")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> {
                    MethodArgumentNotValidException ex = (MethodArgumentNotValidException) result.getResolvedException();
                    assert ex != null;
                    String message = Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage();
                    assertEquals("Number of free Economy rooms cannot be negative", message);
                });
    }

    @Test
    public void whenMissingFreePremiumRooms_thenReturns400() throws Exception {
        // Given
        String requestBody = "{\"freeEconomyRooms\": 5}";

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/calculate-occupancy")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> {
                    MethodArgumentNotValidException ex = (MethodArgumentNotValidException) result.getResolvedException();
                    assertNotNull(ex);
                    String message = Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage();
                    assertEquals("Number of free Premium rooms cannot be null", message);
                });
    }

    @Test
    public void whenMissingFreeEconomyRooms_thenReturns400() throws Exception {
        // Given
        String requestBody = "{\"freePremiumRooms\": 10}";

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/calculate-occupancy")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> {
                    MethodArgumentNotValidException ex = (MethodArgumentNotValidException) result.getResolvedException();
                    assertNotNull(ex);
                    String message = Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage();
                    assertEquals("Number of free Economy rooms cannot be null", message);
                });
    }
}
