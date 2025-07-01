package online.tripguru.backend.features.trips.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TripEventResponse {
    private String day;             // e.g. 2025-07-07
    private String title;           // e.g. "Visit the Palace"
    private String time;            // e.g. "10:00"
    private int durationMinutes;    // e.g. 90
    private String location;
    private String description;
}
