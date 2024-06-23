package com.example.room_occupancy_manager.controller;


import com.example.room_occupancy_manager.model.OccupancyRequest;
import com.example.room_occupancy_manager.model.OccupancyResponse;
import com.example.room_occupancy_manager.service.OccupancyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
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
    @Operation(summary = "Calculate room occupancy and revenue",
            description = "Calculate how many rooms of each category will be occupied and the total revenue based on provided data.")
    @ApiResponse(responseCode = "200", description = "Successful calculation of room occupancy and revenue",
            content = @Content(schema = @Schema(implementation = OccupancyResponse.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input data format or values",
            content = @Content(schema = @Schema(implementation = String.class)))
    public ResponseEntity<OccupancyResponse> calculateOccupancy(@Valid @RequestBody OccupancyRequest request) {
        OccupancyResponse response = roomOccupancyService.calculateOccupancy(request);
        return ResponseEntity.ok(response);
    }

}