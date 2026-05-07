package org.example.webserviceslabb1.controller;

import org.example.webserviceslabb1.dto.ChatRequest;
import org.example.webserviceslabb1.dto.ChatResponse;
import org.example.webserviceslabb1.service.ChatService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public ChatResponse chat(@RequestBody ChatRequest request) {

        return chatService.chat(request);
    }
}
