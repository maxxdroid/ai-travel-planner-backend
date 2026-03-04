package online.tripguru.backend.features.trips.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

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
            // Build request body
            String requestBody = """
                {
                  "model": "gpt-4.1-mini",
                  "input": "%s"
                }
                """.formatted(prompt.replace("\"", "\\\"")); // escape quotes

            // Send POST request to OpenAI
            String response = webClient.post()
                    .uri(openAiApiUrl)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + openAiApiKey)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block(); // synchronous for simplicity; can be reactive

            // Parse response JSON to extract AI text
            JsonNode root = objectMapper.readTree(response);

            // The path may vary depending on API version; with Responses API:
            // root["output"][0]["content"][0]["text"]
            JsonNode outputNode = root.path("output").get(0).path("content").get(0).path("text");

            if (outputNode.isMissingNode()) {
                throw new RuntimeException("AI response missing 'text' field");
            }

            return outputNode.asText();

        } catch (Exception e) {
            throw new RuntimeException("Failed to call OpenAI API", e);
        }
    }

}
