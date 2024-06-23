package com.example.room_occupancy_manager.model;

import lombok.Data;

@Data
public class OccupancyResponse {

    private int usagePremium;
    private double revenuePremium;
    private int usageEconomy;
    private double revenueEconomy;

}
