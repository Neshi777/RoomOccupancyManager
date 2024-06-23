package com.example.room_occupancy_manager.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OccupancyRequest {

    @Schema(description = "Number of free Premium rooms available", example = "3")
    @NotNull(message = "Number of free Premium rooms cannot be null")
    @Min(value = 0, message = "Number of free Premium rooms cannot be negative")
    private Integer freePremiumRooms;

    @Schema(description = "Number of free Economy rooms available", example = "3")
    @NotNull(message = "Number of free Economy rooms cannot be null")
    @Min(value = 0, message = "Number of free Economy rooms cannot be negative")
    private Integer freeEconomyRooms;

}
