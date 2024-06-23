package com.example.room_occupancy_manager.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OccupancyRequest {

    private Integer freePremiumRooms;
    private Integer freeEconomyRooms;

}
