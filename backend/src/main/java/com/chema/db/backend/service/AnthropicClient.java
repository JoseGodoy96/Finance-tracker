package com.chema.db.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AnthropicClient {

    private final RestTemplate restTemplate;

    public AnthropicClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${anthropic.api-key}")
    private String apiKey;

    @Value("${anthropic.model}")
    private String model;

    @Value("${anthropic.api-url}")
    private String apiUrl;

    public String ask(String prompt) {
        return null;
    }
}
