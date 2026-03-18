package online.tripguru.backend.features.trips.entity;

import lombok.*;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Activity {

    private Integer order;

    private String title;

    private String description;

    private Location location;

    private LocalDateTime startTime;

    private ActivityType type;

    private Boolean recommended;

    private Double estimatedCost;

    private Boolean completed;

    private Integer rating;

    private Integer travelTimeMinutes;

    private String transportMode;

    private ActivityStatus status;

    private Integer durationMinutes;

    private String referenceUrl;
}
