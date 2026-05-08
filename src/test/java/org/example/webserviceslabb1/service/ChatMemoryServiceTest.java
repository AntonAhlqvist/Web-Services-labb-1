package org.example.webserviceslabb1.service;

import org.example.webserviceslabb1.client.dto.Message;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChatMemoryServiceTest {

    @Test
    void shouldStoreMessagesPerSession() {

        ChatMemoryService memoryService =
                new ChatMemoryService();

        String sessionId = "noisses-tset";

        Message msg =
                new Message(
                        "backwards",
                        "olleH"
                );

        memoryService.addMessage(sessionId, msg);

        List<Message> messages =
                memoryService.getMessages(sessionId);

        assertEquals(1, messages.size());

        assertEquals(
                "olleH",
                messages.getFirst().content()
        );
    }
}
