# Room Occupancy Manager

This is a Spring Boot application that calculates room occupancy and revenue based on provided data. It uses a set of rules to allocate rooms to guests and calculates the resulting revenue for both premium and economy rooms.

## Table of Contents
- [Overview](#overview)
- [Requirements](#requirements)
- [Installation and Setup](#installation-and-setup)
- [Usage](#usage)
    - [API Endpoints](#api-endpoints)
    - [Example Request](#example-request)
    - [Swagger Documentation](#swagger-documentation)
    - [Accessing Swagger UI](#accessing-swagger-ui)
- [License](#license)

## Overview

This project is a Spring Boot application designed to manage room occupancy and revenue calculation based on provided input data. The application optimizes room occupancy for hotels by allocating available rooms (Premium and Economy) based on guests' willingness to pay. It ensures that higher-paying guests are prioritized for Premium rooms and calculates total revenue based on room allocations. Additionally, it ensures that Economy offers to be upgraded to Premiums if all Economy rooms are taken and at the same time there are free Premium Rooms with priority of higher Economy offers.

The Room Occupancy Manager optimizes hotel room occupancy by recommending the best room assignment strategy based on available Premium and Economy rooms, and guests' willingness to pay. Maximize revenue while ensuring customer satisfaction.

## Requirements

Before you begin, ensure you have met the following requirements:
- Java 17 or higher
- Maven 3.6.0 or higher
- Git (for cloning the repository)

## Installation and Setup

To set up the project locally, follow these steps:

1. **Clone the repository:**
    ```sh
    git clone https://github.com/your-username/room-occupancy-manager.git
    cd room-occupancy-manager
    ```

2. **Build the project using Maven:**
    ```sh
    mvn clean install
    ```

3. **Run the application:**
    ```sh
    mvn spring-boot:run
    ```

The application should now be running on `http://localhost:8080`.

## Usage

### API Endpoints

- `POST /api/calculate-occupancy`: Calculate room occupancy and revenue.

### Example Request

To calculate room occupancy, send a POST request to `/api/calculate-occupancy` with the following JSON body:

```json
{
  "freePremiumRooms": 3,
  "freeEconomyRooms": 3
}
```

### Endpoint test example

You can use curl to send this request from the command line:
```
curl -X POST http://localhost:8080/api/calculate-occupancy \
-H "Content-Type: application/json" \
-d '{"freePremiumRooms":3, "freeEconomyRooms":3}'
```
### Response

The response will be a JSON object with the calculated occupancy and revenue:

```
{
"usagePremium": 3,
"revenuePremium": 450.0,
"usageEconomy": 3,
"revenueEconomy": 150.0
}
```

### Swagger Documentation

The Room Occupancy Manager API is documented using Swagger. You can access the Swagger UI to explore the available endpoints and their details.

### Accessing Swagger UI

1. **Swagger UI**:

    - [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
    or 
    - [http://your-ip-address:8080/swagger-ui/index.html](http://your-ip-address:8080/swagger-ui/index.html)

2. **OpenAPI Documentation**:
    - [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)
   or
    - [http://your-ip-address:8080/v3/api-docs](http://your-ip-address:8080/v3/api-docs)

Replace `your-ip-address` with the actual IP address of your machine.

Swagger UI provides an interactive interface for testing the API endpoints directly from your browser. It also provides detailed information about each endpoint, including request parameters, request body, and responses.

For example, to test the `calculate-occupancy` endpoint, you can use Swagger UI to fill in the required data and execute the request, observing the response returned by the server.
## License
This project is licensed under the MIT License. See the LICENSE file for details.