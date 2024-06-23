package com.example.room_occupancy_manager.service;

import com.example.room_occupancy_manager.model.OccupancyRequest;
import com.example.room_occupancy_manager.model.OccupancyResponse;

public interface OccupancyService {
    OccupancyResponse calculateOccupancy(OccupancyRequest request);
}
