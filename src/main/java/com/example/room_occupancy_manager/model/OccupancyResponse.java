package com.example.room_occupancy_manager.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class OccupancyResponse {

    @Schema(description = "Number of Premium rooms occupied", example = "3")
    private int usagePremium;

    @Schema(description = "Revenue generated from Premium rooms", example = "738.0")
    private double revenuePremium;

    @Schema(description = "Number of Economy rooms occupied", example = "3")
    private int usageEconomy;

    @Schema(description = "Revenue generated from Economy rooms", example = "167.99")
    private double revenueEconomy;

}
