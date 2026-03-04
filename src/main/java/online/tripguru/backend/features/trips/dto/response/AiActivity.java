package online.tripguru.backend.features.trips.dto.response;

public record AiActivity(
        String title,
        String description,
        String type,
        String startTime,
        String endTime,
        Double estimatedCost,
        Integer durationMinutes,
        String url,
        AiLocation location
) {}