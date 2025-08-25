package online.tripguru.backend.features.trips.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import online.tripguru.backend.common.logs.LogHandler;
import online.tripguru.backend.common.response.Response;
import online.tripguru.backend.common.util.ResponseUtil;
import online.tripguru.backend.features.trips.dto.request.TripsRequest;
import online.tripguru.backend.features.trips.dto.response.TripsResponse;
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
                            Map.of("role", "user", "content", prompt)
                    ),
                    "temperature", 0.8
            );

             Mono<TripsResponse> response = webClient.post()
                    .uri(openAiApiUrl)
                    .header("Authorization", "Bearer " + openAiApiKey)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .map(answer -> {
                        try {
                            List<Map<String, Object>> choices = (List<Map<String, Object>>) answer.get("choices");
                            Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                            String rawContent = message.get("content").toString();

                            // Clean up any escape characters and trim quotes
                            String cleanedJson = rawContent
                                    .replace("\\n", "")
                                    .replace("\\\"", "\"")
                                    .replace("\\t", "")
                                    .replaceAll("^\"|\"$", ""); // removes outer quotes if GPT wrapped it

                            // Deserialize into TripPlanResponse
                            ObjectMapper mapper = new ObjectMapper();
                            return mapper.readValue(cleanedJson, TripsResponse.class);
                        } catch (Exception e) {
                            LogHandler.log("Error parsing JSON response: %s", e);
                            return TripsResponse.builder().build();
                        }
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


    private String buildPrompt(String destination, String preferences, String startDate, int numberOfDays) {
        return String.format(
                "Plan a %d-day trip to %s starting on %s. The user is interested in %s. " +
                        "Create 2–3 detailed travel activities per day. Each event should include a title, time (HH:mm), " +
                        "duration in minutes, location, and a short description. " +
                        "Return the result in the following JSON format:\n\n" +
                        "{\n" +
                        "  \"tripTitle\": \"%d-Day Trip to %s\",\n" +
                        "  \"startDate\": \"%s\",\n" +
                        "  \"endDate\": \"YYYY-MM-DD\",\n" +
                        "  \"timezone\": \"Continent/City\",\n" +
                        "  \"events\": [\n" +
                        "    {\n" +
                        "      \"day\": \"YYYY-MM-DD\",\n" +
                        "      \"title\": \"Event title\",\n" +
                        "      \"time\": \"HH:mm\",\n" +
                        "      \"durationMinutes\": 90,\n" +
                        "      \"location\": \"Event location\",\n" +
                        "      \"description\": \"Short description\"\n" +
                        "    },\n" +
                        "    ...\n" +
                        "  ]\n" +
                        "}\n\n" +
                        "Use realistic local places, dishes, and experiences from %s. " +
                        "Return only valid JSON. Do not include any explanation or notes.",
                numberOfDays, destination, startDate, numberOfDays, destination, startDate, destination
        );
    }

    private String buildPrompt(String destination, String preferences) {
        return String.format(
                "Plan a 5-day trip to %s for a user who prefers %s. " +
                        "Start the trip on a Monday. For each day, create 2–3 detailed travel activities. " +
                        "Return the result as a structured JSON object with the following format:\n\n" +
                        "{\n" +
                        "  \"tripTitle\": \"5-Day Trip to [Destination]\",\n" +
                        "  \"startDate\": \"YYYY-MM-DD\",\n" +
                        "  \"endDate\": \"YYYY-MM-DD\",\n" +
                        "  \"timezone\": \"Continent/City\",\n" +
                        "  \"events\": [\n" +
                        "    {\n" +
                        "      \"day\": \"YYYY-MM-DD\",\n" +
                        "      \"title\": \"Event title\",\n" +
                        "      \"time\": \"HH:mm\",\n" +
                        "      \"durationMinutes\": 90,\n" +
                        "      \"location\": \"Event location\",\n" +
                        "      \"description\": \"Short description\"\n" +
                        "    },\n" +
                        "    ...\n" +
                        "  ]\n" +
                        "}\n\n" +
                        "Ensure all events are calendar-friendly with valid times and durations. " +
                        "Use real examples for food, attractions, and cultural experiences in %s. " +
                        "Don't include any additional commentary — only return valid JSON.",
                destination, preferences, destination
        );
    }

}
