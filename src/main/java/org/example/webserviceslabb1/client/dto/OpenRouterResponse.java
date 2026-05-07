package org.example.webserviceslabb1.client.dto;

import java.util.List;

public record OpenRouterResponse(

        List<Choice> choices

) {
}
