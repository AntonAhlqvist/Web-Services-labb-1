package org.example.webserviceslabb1.client.dto;

import java.util.List;

public record OpenRouterRequest(

        String model,

        List<Message> messages

) {
}
