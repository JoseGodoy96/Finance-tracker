package com.chema.db.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        // 1. Cabeceras: la key, la versión de la API y el content-type
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", apiKey);
        headers.set("anthropic-version", "2023-06-01");
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 2. Body: modelo, max_tokens y el mensaje del usuario
        Map<String, Object> body = new HashMap<>();
        body.put("model", model);
        body.put("max_tokens", 100);
        body.put("messages", List.of(
                Map.of("role", "user", "content", prompt)
        ));

        // 3. Se juntan cabeceras + body en una "HttpEntity"
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        // 4. Se llama a la API
        ResponseEntity<Map> response = restTemplate.postForEntity(apiUrl, request, Map.class);

        // 5. Se saca el texto de la respuesta: content[0].text
        List<Map<String, Object>> content = (List<Map<String, Object>>) response.getBody().get("content");
        return (String) content.get(0).get("text");
    }
}
