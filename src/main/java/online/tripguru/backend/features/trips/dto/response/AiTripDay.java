package online.tripguru.backend.features.trips.dto.response;

import java.util.List;

public record AiTripDay(
        String date,
        String notes,
        AiStay stay,
        List<AiActivity> activities
) {}