package org.example.webserviceslabb1.client;

import org.example.webserviceslabb1.client.dto.Message;
import org.example.webserviceslabb1.client.dto.OpenRouterRequest;
import org.example.webserviceslabb1.client.dto.OpenRouterResponse;
import org.example.webserviceslabb1.service.ChatMemoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.ArrayList;
import java.util.List;

/**
 * Client responsible for communication with the OpenRouter API.
 * <p>
 * Builds AI chat requests using a selected personality,
 * previous conversation history and the latest user message.
 * <p>
 * Sends requests using Spring RestClient and extracts
 * the assistant response from the API response body.
 * <p>
 * Conversation history is stored in memory per session id
 * through ChatMemoryService.
 */
@Component
public class OpenRouterClient {

    private static final Logger log =
            LoggerFactory.getLogger(OpenRouterClient.class);

    private static final String EMPTY_RESPONSE_MESSAGE = """
            AI service returned an empty response.
            
            Please try again later.
            """;

    private static final String ERROR_MESSAGE = """
            The AI service is currently unavailable.
            
            This may be caused by:
            - temporary network issues
            - rate limiting
            - unavailable upstream provider
            - invalid or missing API credits
            
            Please try again later.
            """;

    private final RestClient restClient;
    private final ChatMemoryService chatMemoryService;

    public OpenRouterClient(
            @Value("${openrouter.base-url}") String baseUrl,
            @Value("${openrouter.api-key}") String apiKey,
            ChatMemoryService chatMemoryService
    ) {

        this.chatMemoryService = chatMemoryService;

        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(
                        "Authorization",
                        "Bearer " + apiKey
                )
                .build();
    }

    public String ask(
            String personality,
            String userMessage,
            String sessionId
    ) {

        List<Message> messages = new ArrayList<>();

        messages.add(
                new Message(
                        "system",
                        getSystemPrompt(personality)
                )
        );

        List<Message> previousMessages =
                chatMemoryService.getMessages(sessionId);

        messages.addAll(previousMessages);

        Message userMsg =
                new Message(
                        "user",
                        userMessage
                );

        messages.add(userMsg);

        OpenRouterRequest request =
                new OpenRouterRequest(
                        "openai/gpt-4o-mini",
                        messages
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

                return EMPTY_RESPONSE_MESSAGE;
            }

            Message assistantMessage =
                    response
                            .choices()
                            .getFirst()
                            .message();

            if (assistantMessage == null
                    || assistantMessage.content() == null
                    || assistantMessage.content().isBlank()) {

                return EMPTY_RESPONSE_MESSAGE;
            }

            String assistantReply =
                    assistantMessage.content();

            chatMemoryService.addMessage(
                    sessionId,
                    userMsg
            );

            chatMemoryService.addMessage(
                    sessionId,
                    new Message(
                            "assistant",
                            assistantReply
                    )
            );

            return assistantReply;

        } catch (RestClientException e) {

            log.warn(
                    "OpenRouter request failed for sessionId={}",
                    sessionId,
                    e
            );

            return ERROR_MESSAGE;

        } catch (Exception e) {

            log.error(
                    "Unexpected error in OpenRouterClient for sessionId={}",
                    sessionId,
                    e
            );

            return ERROR_MESSAGE;
        }
    }

    private String getSystemPrompt(String personality) {

        if (personality == null || personality.isBlank()) {
            return "You are a generic AI assistant.";
        }

        return switch (personality.toLowerCase()) {

            case "coder" -> "You are a skilled Java developer who explains things clearly.";

            case "gordon ramsay" -> "You speak like Gordon Ramsay.";

            case "backwards" -> "You must answer every response with the words written in reverse order.";

            default -> "You are a generic AI assistant.";
        };
    }
}
