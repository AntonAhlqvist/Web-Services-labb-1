package org.example.webserviceslabb1.client;

import org.example.webserviceslabb1.client.dto.Message;
import org.example.webserviceslabb1.client.dto.OpenRouterRequest;
import org.example.webserviceslabb1.client.dto.OpenRouterResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class OpenRouterClient {

    private final RestClient restClient;

    public OpenRouterClient(
            @Value("${openrouter.base-url}") String baseUrl,
            @Value("${openrouter.api-key}") String apiKey
    ) {

        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(
                        "Authorization",
                        "Bearer " + apiKey
                )
                .build();
    }

    public String ask(String personality, String userMessage) {

        OpenRouterRequest request =
                new OpenRouterRequest(
                        "openai/gpt-4o-mini",
                        List.of(
                                new Message(
                                        "system",
                                        getSystemPrompt(personality)
                                ),
                                new Message(
                                        "user",
                                        userMessage
                                )
                        )
                );

        try {

            OpenRouterResponse response =
                    restClient.post()
                            .uri("/chat/completions")
                            .body(request)
                            .retrieve()
                            .body(OpenRouterResponse.class);

            if (response == null
                    || response.choices() == null
                    || response.choices().isEmpty()) {

                return """
                    AI service returned an empty response.
                    
                    Please try again later.
                    """;
            }

            return response
                    .choices()
                    .getFirst()
                    .message()
                    .content();

        } catch (Exception e) {

            return """
                The AI service is currently unavailable.
                
                This may be caused by:
                - temporary network issues
                - rate limiting
                - unavailable upstream provider
                - invalid or missing API credits
                
                Please try again later.
                """;
        }
    }

    private String getSystemPrompt(String personality) {

        if (personality == null || personality.isBlank()) {
            return "You are a generic AI assistant.";
        }

        return switch (personality.toLowerCase()) {

            case "coder" ->
                    "You are a skilled Java developer who explains things clearly.";

            case "gordon ramsay" ->
                    "You speak like Gordon Ramsay.";

            case "backwards" ->
                    "You must answer every response with the words written in reverse order.";

            default ->
                    "You are a generic AI assistant.";
        };
    }
}
