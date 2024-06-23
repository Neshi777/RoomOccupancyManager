package com.example.room_occupancy_manager.service;

import com.example.room_occupancy_manager.model.OccupancyRequest;
import com.example.room_occupancy_manager.model.OccupancyResponse;
import org.springframework.stereotype.Service;

@Service
public class OccupancyServiceImpl implements OccupancyService{

    @Override
    public OccupancyResponse calculateOccupancy(OccupancyRequest request) {
        return new OccupancyResponse();
    }

}
