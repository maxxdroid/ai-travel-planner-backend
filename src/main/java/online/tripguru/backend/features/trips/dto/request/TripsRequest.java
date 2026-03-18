package online.tripguru.backend.features.trips.dto.request;

import online.tripguru.backend.features.trips.entity.BudgetRange;

import java.time.LocalDateTime;
import java.util.List;

public record TripsRequest(
        String destination,
        String preferences,
        LocalDateTime startDate,
        LocalDateTime endDate,
        int travelers,
        BudgetRange budgetRange,
        List<String> interests,
        String tripDescription
) {}
