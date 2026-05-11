package org.example.webserviceslabb1.service;

import org.example.webserviceslabb1.client.OpenRouterClient;
import org.example.webserviceslabb1.dto.ChatRequest;
import org.example.webserviceslabb1.dto.ChatResponse;
import org.springframework.stereotype.Service;

/**
 * Handles chat requests and coordinates communication
 * with the AI client.
 * <p>
 * Receives user input from the controller layer,
 * forwards the request to OpenRouterClient and
 * returns the AI response wrapped in a ChatResponse DTO.
 * <p>
 * Also forwards the selected personality and optional
 * session id used for conversation memory.
 */
@Service
public class ChatService {

    private final OpenRouterClient openRouterClient;

    public ChatService(OpenRouterClient openRouterClient) {
        this.openRouterClient = openRouterClient;
    }

    public ChatResponse chat(ChatRequest request) {

        String reply =
                openRouterClient.ask(
                        request.personality(),
                        request.message(),
                        request.sessionId()
                );

        return new ChatResponse(reply);
    }
}
