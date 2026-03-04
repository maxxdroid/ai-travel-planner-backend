package online.tripguru.backend.features.trips.dto.response;

import java.util.List;

public record AiTripResponse(
        String title,
        String city,
        String country,
        List<AiTripDay> days
) {}