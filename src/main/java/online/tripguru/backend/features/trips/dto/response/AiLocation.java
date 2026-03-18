package online.tripguru.backend.features.trips.dto.response;

public record AiLocation(
        String name,
        Double latitude,
        Double longitude,
        String address
) {}