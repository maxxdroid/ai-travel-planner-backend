package online.tripguru.backend.features.trips.controller;

import lombok.RequiredArgsConstructor;
import online.tripguru.backend.common.response.Response;
import online.tripguru.backend.features.trips.dto.request.TripsRequest;
import online.tripguru.backend.features.trips.entity.Trip;
import online.tripguru.backend.features.trips.service.GuruService;
import online.tripguru.backend.features.trips.service.TripService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/trips")
@RequiredArgsConstructor
public class TripController {
    private final GuruService tripService;

    @GetMapping
    public ResponseEntity<Response<Trip>> getTripPlans (TripsRequest tripsRequest) {
        return tripService.create(tripsRequest);
    }

}
