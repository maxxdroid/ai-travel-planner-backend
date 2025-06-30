package online.tripguru.backend.features.trips.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import online.tripguru.backend.commons.logging.LogHandler;
import online.tripguru.backend.commons.response.Response;
import online.tripguru.backend.commons.util.ResponseUtil;
import online.tripguru.backend.features.trips.dto.request.TripsRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TripServiceImpl implements TripService{

    @Value("${openai.api.key}")
    private String openAiApiKey;

    @Value("${openai.api.url}")
    private String openAiApiUrl;

    private final WebClient webClient;

    @Override
    public ResponseEntity<Response<?>> getTrips(TripsRequest request) {

        try {
            String prompt = buildPrompt(request.destination(), request.preferences());

            Map<String, Object> requestBody = Map.of(
                    "model", "gpt-3.5-turbo",
                    "messages", List.of(
                            Map.of("role", "system", "content", "You are a travel planner Ai"),
                            Map.of("role", "user", "content", "prompt")
                    ),
                    "temperature", "0.8"
            );

            Mono<String> response = webClient.post()
                    .uri(openAiApiUrl)
                    .header("Authorization", "Bearer " + openAiApiKey)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .map(answer -> {
                        List<Map<String, Object>> choices = (List<Map<String, Object>>) answer.get("choices");
                        Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                        return message.get("content").toString();
                    });

            Response<?> userResponse = Response.builder()
                    .status(200)
                    .message("Trip plan fetched successfully")
                    .data(response.block())
                    .build();

            return ResponseUtil.success(userResponse);

        } catch (Exception e) {
            LogHandler.log("An error occurred while fetching trips: %s", e);
            return ResponseUtil.serverError("An error occurred while fetching trips");
        }
    }


    private String buildPrompt(String destination, String preferences) {
        return String.format(
                "Plan a 5-day trip to %s. The user is looking for %s. Include 2–3 activities per day, local food suggestions, and travel time between activities. Return the plan in structured JSON format with days as keys.",
                destination, preferences
        );
    }
}
