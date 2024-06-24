package com.example.room_occupancy_manager;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@OpenAPIDefinition(info = @Info(
        title = "Room Occupancy Manager API",
        version = "1.0",
        description = """
                The Room Occupancy Manager application provides an interface for hotels to enter the number of available
                Premium and Economy rooms per night. Upon entering this data, the application immediately calculates the
                occupancy of each room category and the estimated total revenue. The application is designed to
                facilitate room availability management and revenue planning for hotels.
                """))
public class RoomOccupancyManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RoomOccupancyManagerApplication.class, args);
    }

}