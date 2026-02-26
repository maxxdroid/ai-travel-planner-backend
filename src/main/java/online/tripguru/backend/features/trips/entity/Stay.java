package online.tripguru.backend.features.trips.entity;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Stay {

    private String name;

    private Location location;

    private Double pricePerNight;

    private Integer rating; // 1–5

    private String referenceUrl;

    private String notes; // AI recommendation reason
}