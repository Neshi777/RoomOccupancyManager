package com.example.room_occupancy_manager.service;

import com.example.room_occupancy_manager.data_loader.JsonDataLoader;
import com.example.room_occupancy_manager.model.OccupancyRequest;
import com.example.room_occupancy_manager.model.OccupancyResponse;
import com.example.room_occupancy_manager.model.PotentialOccupancies;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OccupancyServiceImpl implements OccupancyService {
    static final double PREMIUM_PRICE_THRESHOLD = 100;
    private final JsonDataLoader jsonDataLoader;
    @Value("${potential.occupancies.file}")
    private String potentialOccupanciesFilePath;

    @Autowired
    public OccupancyServiceImpl(JsonDataLoader jsonDataLoader) {
        this.jsonDataLoader = jsonDataLoader;
    }

    @Override
    public OccupancyResponse calculateOccupancy(OccupancyRequest request) {
        validateInput(request);

        PotentialOccupancies potentialOccupancies = jsonDataLoader.loadPotentialOccupanciesFromFile(potentialOccupanciesFilePath);
        List<Double> premiumOffers = filterAndSortOffers(potentialOccupancies.getOffers(), true);
        List<Double> economyOffers = filterAndSortOffers(potentialOccupancies.getOffers(), false);
        int freePremiumRooms = request.getFreePremiumRooms();
        int freeEconomyRooms = request.getFreeEconomyRooms();

        int usedPremiumRooms = Math.min(freePremiumRooms, premiumOffers.size());
        double premiumRevenue = premiumOffers.stream().limit(usedPremiumRooms).mapToDouble(Double::doubleValue).sum();
        int remainingPremiumRooms = freePremiumRooms - usedPremiumRooms;
        int economyOffersSurplus = economyOffers.size() - freeEconomyRooms;
        int offersToBeUpgraded = 0;

        if (economyOffersSurplus > 0 && remainingPremiumRooms > 0) {
            offersToBeUpgraded = Math.min(remainingPremiumRooms, economyOffersSurplus);
            premiumRevenue += calculateUpgradedRevenue(economyOffers, offersToBeUpgraded);
            usedPremiumRooms += offersToBeUpgraded;
        }

        int usedEconomyRooms = Math.min(freeEconomyRooms, economyOffers.size());
        double economyRevenue = economyOffers.stream().skip(offersToBeUpgraded).limit(usedEconomyRooms).mapToDouble(Double::doubleValue).sum();

        return createResponse(usedPremiumRooms, premiumRevenue, usedEconomyRooms, economyRevenue);
    }

    private void validateInput(OccupancyRequest request) {
        if (request.getFreePremiumRooms() < 0 || request.getFreeEconomyRooms() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Number of rooms cannot be negative");
        }
    }

    private List<Double> filterAndSortOffers(List<Double> offers, boolean premium) {
        return offers.stream()
                .filter(offer -> (premium && offer >= PREMIUM_PRICE_THRESHOLD) || (!premium && offer < PREMIUM_PRICE_THRESHOLD))
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
    }

    private double calculateUpgradedRevenue(List<Double> economyOffers, int offersToBeUpgraded) {
        return economyOffers.stream()
                .limit(offersToBeUpgraded)
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    private OccupancyResponse createResponse(int usedPremiumRooms, double premiumRevenue, int usedEconomyRooms, double economyRevenue) {
        OccupancyResponse response = new OccupancyResponse();
        response.setUsagePremium(usedPremiumRooms);
        response.setRevenuePremium(premiumRevenue);
        response.setUsageEconomy(usedEconomyRooms);
        response.setRevenueEconomy(economyRevenue);
        log.info("Calculated occupancy: {}", response);
        return response;
    }

}