package org.example.webserviceslabb1.service;

import org.example.webserviceslabb1.client.dto.Message;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Stores chat history in memory per session id.
 * <p>
 * Each session keeps its own list of messages,
 * allowing the AI model to receive previous
 * conversation context in future requests.
 * <p>
 * Message history is stored using a simple
 * in-memory HashMap and is not persisted.
 */
@Service
public class ChatMemoryService {

    private final Map<String, List<Message>> memory =
            new HashMap<>();

    public List<Message> getMessages(String sessionId) {

        if (sessionId == null || sessionId.isBlank()) {
            return new ArrayList<>();
        }

        return memory.computeIfAbsent(
                sessionId,
                id -> new ArrayList<>()
        );
    }

    public void addMessage(
            String sessionId,
            Message message
    ) {

        if (sessionId == null || sessionId.isBlank()) {
            return;
        }

        memory.computeIfAbsent(
                sessionId,
                id -> new ArrayList<>()
        ).add(message);
    }
}
