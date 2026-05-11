package org.example.webserviceslabb1.service;

import org.example.webserviceslabb1.client.dto.Message;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

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
            new ConcurrentHashMap<>();

    public List<Message> getMessages(String sessionId) {

        if (sessionId == null || sessionId.isBlank()) {
            return List.of();
        }

        List<Message> messages =
                memory.computeIfAbsent(
                        sessionId,
                        id -> Collections.synchronizedList(
                                new ArrayList<>()
                        )
                );

        synchronized (messages) {
            return new ArrayList<>(messages);
        }
    }

    public void addMessage(
            String sessionId,
            Message message
    ) {

        if (sessionId == null
                || sessionId.isBlank()
                || message == null) {

            return;
        }

        memory.computeIfAbsent(
                sessionId,
                id -> Collections.synchronizedList(
                        new ArrayList<>()
                )
        ).add(message);
    }
}
