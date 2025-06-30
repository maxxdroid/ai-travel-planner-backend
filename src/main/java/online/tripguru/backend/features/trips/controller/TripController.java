package online.tripguru.backend.features.trips.controller;

import lombok.RequiredArgsConstructor;
import online.tripguru.backend.commons.response.Response;
import online.tripguru.backend.features.trips.dto.request.TripsRequest;
import online.tripguru.backend.features.trips.service.TripService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/trips")
@RequiredArgsConstructor
public class TripController {
    private final TripService tripService;

    @GetMapping
    public ResponseEntity<Response<?>> getTripPlans (TripsRequest tripsRequest) {
        return tripService.getTrips(tripsRequest);
    }

}
