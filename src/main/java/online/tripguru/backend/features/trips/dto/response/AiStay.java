package online.tripguru.backend.features.trips.dto.response;

public record AiStay(
        String name,
        Double pricePerNight,
        Integer rating,
        String bookingUrl,
        AiLocation location
) {}
