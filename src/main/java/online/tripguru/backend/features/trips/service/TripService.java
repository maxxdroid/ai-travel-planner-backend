package online.tripguru.backend.features.trips.service;

import online.tripguru.backend.commons.response.Response;
import online.tripguru.backend.features.trips.dto.request.TripsRequest;
import org.springframework.http.ResponseEntity;

public interface TripService {

    ResponseEntity<Response<?>> getTrips(TripsRequest request);
}
