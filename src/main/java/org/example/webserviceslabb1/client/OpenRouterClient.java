package org.example.webserviceslabb1.client;

import org.example.webserviceslabb1.client.dto.Message;
import org.example.webserviceslabb1.client.dto.OpenRouterRequest;
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

        return """
                TODO: Real OpenRouter call
                
                Model:
                %s
                
                Personality:
                %s
                
                User said:
                %s
                """
                .formatted(
                        request.model(),
                        personality,
                        userMessage
                );
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
                    "You answer everything backwards.";

            default ->
                    "You are a generic AI assistant.";
        };
    }
}
