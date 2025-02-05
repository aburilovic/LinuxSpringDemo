package com.aburilovic.springbootsandbox.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/chat")
@RequiredArgsConstructor
@Slf4j
public class AIController {
    private static final String API_URL = "https://api-inference.huggingface.co/models/deepseek-ai/DeepSeek-R1-Distill-Qwen-32B";
    @Value("${huggingface.api.token}")
    private String apiToken;

    @PostMapping("/ask")
    public ResponseEntity<String> askAI(@RequestBody Map<String, String> request) {
        final String userInput = request.get("question");
        final String response = callHuggingFaceAPI(userInput);
        return ResponseEntity.ok(response);
    }

    private String callHuggingFaceAPI(String input) {
        final RestTemplate restTemplate = new RestTemplate();
        final HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        final Map<String, Object> body = new HashMap<>();
        body.put("inputs", input);

        final HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        final ResponseEntity<String> response = restTemplate.exchange(
                API_URL,
                HttpMethod.POST, request, String.class);

        return response.getBody();
    }
}
