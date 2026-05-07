package org.example.webserviceslabb1.service;

import org.example.webserviceslabb1.client.OpenRouterClient;
import org.example.webserviceslabb1.dto.ChatRequest;
import org.example.webserviceslabb1.dto.ChatResponse;
import org.springframework.stereotype.Service;

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
                        request.message()
                );

        return new ChatResponse(reply);
    }
}
