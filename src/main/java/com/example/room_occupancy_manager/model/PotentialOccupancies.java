package com.example.room_occupancy_manager.model;

import lombok.Data;

import java.util.List;

@Data
public class PotentialOccupancies {
    private List<Double> offers;
}
