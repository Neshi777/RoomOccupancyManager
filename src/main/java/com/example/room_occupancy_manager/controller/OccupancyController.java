package com.example.room_occupancy_manager.controller;


import com.example.room_occupancy_manager.model.OccupancyRequest;
import com.example.room_occupancy_manager.model.OccupancyResponse;
import com.example.room_occupancy_manager.service.OccupancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OccupancyController {

    private final OccupancyService roomOccupancyService;

    @Autowired
    public OccupancyController(OccupancyService roomOccupancyService) {
        this.roomOccupancyService = roomOccupancyService;
    }

    @PostMapping("/calculate-occupancy")
    public ResponseEntity<OccupancyResponse> calculateOccupancy(@RequestBody OccupancyRequest request) {
        OccupancyResponse response = roomOccupancyService.calculateOccupancy(request);
        return ResponseEntity.ok(response);
    }

}