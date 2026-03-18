package online.tripguru.backend.features.trips.service;

import online.tripguru.backend.common.response.Response;
import online.tripguru.backend.features.trips.dto.request.TripsRequest;
import org.springframework.http.ResponseEntity;

public interface TripService extends GuruService {

    ResponseEntity<Response<?>> getTrips(TripsRequest request);
}
