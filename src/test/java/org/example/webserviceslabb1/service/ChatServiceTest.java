package org.example.webserviceslabb1.service;

import org.example.webserviceslabb1.client.OpenRouterClient;
import org.example.webserviceslabb1.dto.ChatRequest;
import org.example.webserviceslabb1.dto.ChatResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ChatServiceTest {

    @Test
    void shouldReturnReplyFromOpenRouterClient() {

        OpenRouterClient openRouterClient =
                mock(OpenRouterClient.class);

        when(openRouterClient.ask(
                anyString(),
                anyString(),
                anyString()
        )).thenReturn(
                "This code is so bland it needs salt. And while we're at it, throw in some scrambled eggs."
        );

        ChatService chatService =
                new ChatService(openRouterClient);

        ChatRequest request =
                new ChatRequest(
                        "gordon ramsay",
                        "Can you review my Java code?",
                        "hells-kitchen-episode"
                );

        ChatResponse response =
                chatService.chat(request);

        assertEquals(
                "This code is so bland it needs salt. And while we're at it, throw in some scrambled eggs.",
                response.reply()
        );
    }
}
