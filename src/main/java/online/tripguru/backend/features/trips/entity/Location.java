package online.tripguru.backend.features.trips.entity;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    private String name;
    private Double latitude;
    private Double longitude;
    private String address;
}
