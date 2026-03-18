package online.tripguru.backend.features.trips.service;

import online.tripguru.backend.common.response.Response;
import online.tripguru.backend.features.trips.dto.request.TripsRequest;
import online.tripguru.backend.features.trips.entity.Trip;
import org.springframework.http.ResponseEntity;

public interface GuruService {

    ResponseEntity<Response<Trip>> create (TripsRequest tr);

}
