package org.example.webserviceslabb1.service;

import org.example.webserviceslabb1.dto.ChatRequest;
import org.example.webserviceslabb1.dto.ChatResponse;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    public ChatResponse chat(ChatRequest request) {

        String fakeReply = """
                Personality: %s
                
                You said:
                %s
                """
                .formatted(
                        request.personality(),
                        request.message()
                );

        return new ChatResponse(fakeReply);
    }
}
