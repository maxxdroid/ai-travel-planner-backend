package online.tripguru.backend.features.trips.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OpenAiClient {

    @Value("${openai.api.key}")
    private String openAiApiKey;

    @Value("${openai.api.url}")
    private String openAiApiUrl;

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    /**
     * Sends the prompt to OpenAI and expects a JSON string response.
     */
    public String generateStructuredItinerary(String prompt) {

        try {

            // Build request body safely as a Map (no escaping needed)
            Map<String, Object> body = Map.of(
                    "model", "gpt-4o",
                    "input", prompt,       // <-- string, not nested
                    "temperature", 0.7
            );

            // Send request
            String response = webClient.post()
                    .uri(openAiApiUrl)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + openAiApiKey)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .bodyValue(body)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            System.out.println("OPENAI RAW RESPONSE:\n" + response);

            // Parse AI JSON text from Responses API
            JsonNode root = objectMapper.readTree(response);
            JsonNode textNode = root.path("output").get(0).path("content").get(0).path("text");

            if (textNode.isMissingNode()) {
                throw new RuntimeException("AI response missing 'text' field");
            }

            return textNode.asText();

        } catch (Exception e) {
            throw new RuntimeException("Failed to call OpenAI API", e);
        }
    }

}
