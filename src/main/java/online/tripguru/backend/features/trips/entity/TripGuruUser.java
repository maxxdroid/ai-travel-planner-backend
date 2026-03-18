package online.tripguru.backend.features.trips.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TripGuruUser {
    private String id;
    private String fullName;
    private TripRole role;
}
