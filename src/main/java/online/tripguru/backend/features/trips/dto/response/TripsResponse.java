package online.tripguru.backend.features.trips.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TripsResponse {
    private String tripTitle;
    private String startDate;    // format: YYYY-MM-DD
    private String endDate;      // format: YYYY-MM-DD
    private String timezone;
    private List<TripEventResponse> events;
}
