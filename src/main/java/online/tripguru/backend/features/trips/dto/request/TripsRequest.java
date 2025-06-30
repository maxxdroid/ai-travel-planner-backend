package online.tripguru.backend.features.trips.dto.request;

public record TripsRequest(
        String destination,
        String preferences
) {}
