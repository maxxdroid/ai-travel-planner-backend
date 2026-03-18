package online.tripguru.backend.features.trips.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import online.tripguru.backend.common.factories.ResponseFactory;
import online.tripguru.backend.common.response.Response;
import online.tripguru.backend.features.trips.dto.request.TripsRequest;
import online.tripguru.backend.features.trips.dto.response.AiActivity;
import online.tripguru.backend.features.trips.dto.response.AiLocation;
import online.tripguru.backend.features.trips.dto.response.AiStay;
import online.tripguru.backend.features.trips.dto.response.AiTripResponse;
import online.tripguru.backend.features.trips.entity.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GuruServiceImpl implements GuruService{

    @Value("${openai.api.key}")
    private String openAiApiKey;

    @Value("${openai.api.url}")
    private String openAiApiUrl;

    private final OpenAiClient openAiClient;

    private final ObjectMapper objectMapper;

    private final WebClient webClient;

    TripGuruUser guruUser =  TripGuruUser
            .builder()
            .role(TripRole.ADMIN)
            .fullName("Maxwell Antwi")
            .id("asd-gfhd-kilsd-op-ds")
            .build();


    public Trip generateTrip(TripsRequest request, TripGuruUser planner) {

        // Build prompt
        String prompt = buildPrompt(request);

        // Call OpenAI using the fixed client
        String aiJson = openAiClient.generateStructuredItinerary(prompt);

        // Parse JSON into your AiTripResponse record
        AiTripResponse response = parse(aiJson);

        // Map to Trip entity
        return mapToEntity(response, request, planner);
    }


    private String buildPrompt(TripsRequest request) {
        return """
        Create a realistic travel itinerary based on real-world travel behavior.

        REQUIREMENTS:
        - Respect travel time between locations
        - Activities must follow real opening hours
        - Group activities by geographic proximity
        - Max 4 major activities per day
        - Include meal experiences
        - Include realistic pacing (breaks, rest)
        - Provide an accommodation recommendation for each night
        - Budget must match: %s
        - Travelers: %d
        - Interests: %s
        - Description: %s

        TRIP DATES:
        Start: %s
        End: %s

        OUTPUT STRICT JSON matching schema:

        {
          "title": "...",
          "city": "...",
          "country": "...",
          "days": [
            {
              "date": "YYYY-MM-DD",
              "notes": "...",
              "stay": {
                "name": "...",
                "pricePerNight": 0,
                "rating": 0,
                "bookingUrl": "...",
                "location": {
                  "name": "...",
                  "latitude": 0,
                  "longitude": 0,
                  "address": "..."
                }
              },
              "activities": [
                {
                  "title": "...",
                  "description": "...",
                  "type": "SIGHTSEEING | FOOD | ENTERTAINMENT | RELAXATION | SHOPPING",
                  "startTime": "YYYY-MM-DDTHH:mm",
                  "endTime": "YYYY-MM-DDTHH:mm",
                  "estimatedCost": 0,
                  "location": {
                    "name": "...",
                    "latitude": 0,
                    "longitude": 0,
                    "address": "..."
                  },
                  "durationMinutes": 0,
                  "url": "..."
                }
              ]
            }
          ]
        }

        NO markdown.
        NO explanations.
        ONLY JSON.
        """.formatted(
                request.budgetRange(),
                request.travelers(),
                request.interests(),
                request.tripDescription(),
                request.startDate(),
                request.endDate()
        );
    }

    private AiTripResponse parse(String json) {
        try {
            return objectMapper.readValue(json, AiTripResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse AI itinerary", e);
        }
    }

    private Trip mapToEntity(AiTripResponse ai, TripsRequest request, TripGuruUser planner) {

        List<TripDay> days = ai.days().stream().map(d ->
                TripDay.builder()
                        .date(LocalDate.parse(d.date()))
                        .notes(d.notes())
                        .stay(mapStay(d.stay()))
                        .activities(d.activities().stream().map(this::mapActivity).toList())
                        .build()
        ).toList();

        return Trip.builder()
                .id(UUID.randomUUID().toString())
                .title(ai.title())
                .country(ai.country())
                .startTime(request.startDate())
                .endTime(request.endDate())
                .aiGenerated(true)
                .aiModel("gpt-4")
                .planner(planner)
                .tripGoers(List.of(planner))
                .days(days)
                .build();
    }

    private Stay mapStay(AiStay s) {
        return Stay.builder()
                .name(s.name())
                .pricePerNight(s.pricePerNight())
                .rating(s.rating())
                .referenceUrl(s.bookingUrl())
                .location(mapLocation(s.location()))
                .notes("AI recommended based on location and budget")
                .build();
    }

    private Activity mapActivity(AiActivity a) {
        return Activity.builder()
                .title(a.title())
                .description(a.description())
                .type(ActivityType.valueOf(a.type()))
                .startTime(LocalDateTime.parse(a.startTime()))
                .estimatedCost(a.estimatedCost())
                .durationMinutes(a.durationMinutes())
                .location(mapLocation(a.location()))
                .recommended(true)
                .status(ActivityStatus.PENDING)
                .completed(false)
                .referenceUrl(a.url())
                .build();
    }

    private Location mapLocation(AiLocation l) {
        return Location.builder()
                .name(l.name())
                .latitude(l.latitude())
                .longitude(l.longitude())
                .address(l.address())
                .build();
    }

    @Override
    public ResponseEntity<Response<Trip>> create(TripsRequest tr) {

        Trip trip = generateTrip(tr, guruUser);

        return ResponseFactory.success(trip);
    }
}
