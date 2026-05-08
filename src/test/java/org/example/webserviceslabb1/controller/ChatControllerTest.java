package org.example.webserviceslabb1.controller;

import org.example.webserviceslabb1.dto.ChatRequest;
import org.example.webserviceslabb1.dto.ChatResponse;
import org.example.webserviceslabb1.service.ChatService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ChatControllerTest {

    @Test
    void shouldReturnChatResponse() {

        ChatService chatService =
                mock(ChatService.class);

        ChatController controller =
                new ChatController(chatService);

        ChatRequest request =
                new ChatRequest(
                        "gordon ramsay",
                        "Can you review my Java code?",
                        "kitchen-nightmare-episode"
                );

        ChatResponse expectedResponse =
                new ChatResponse(
                        "This loop is overcooked!"
                );

        when(chatService.chat(request))
                .thenReturn(expectedResponse);

        ChatResponse actualResponse =
                controller.chat(request);

        assertEquals(
                "This loop is overcooked!",
                actualResponse.reply()
        );
    }
}
