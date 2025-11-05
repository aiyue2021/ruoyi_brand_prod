package com.ruoyi.framework.web.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.system.dto.DeepSeekRequest;
import com.ruoyi.system.dto.DeepSeekResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DeepSeekService {
    
    private static final String DEEPSEEK_API_URL = "https://api.deepseek.com/chat/completions";
    
    @Value("${deepseek.api.key:}")
    private String apiKey;
    
    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    
    public DeepSeekService(WebClient webClient, ObjectMapper objectMapper) {
        this.webClient = webClient;
        this.objectMapper = objectMapper;
    }
    
    /**
     * 普通聊天
     */
    public String chat(String userMessage) {
        return chat(userMessage, null);
    }
    
    /**
     * 带系统提示的聊天
     */
    public String chat(String userMessage, String systemPrompt) {
        try {
            List<DeepSeekRequest.Message> messages = new ArrayList<>();
            
            if (systemPrompt != null && !systemPrompt.trim().isEmpty()) {
                messages.add(DeepSeekRequest.Message.systemMessage(systemPrompt));
            }
            
            messages.add(DeepSeekRequest.Message.userMessage(userMessage));
            
            DeepSeekRequest request = new DeepSeekRequest();
            request.setMessages(messages);
            request.setTemperature(0.7);
            request.setMax_tokens(2048);
            
            log.info("Sending request to DeepSeek API");
            
            DeepSeekResponse response = webClient.post()
                .uri(DEEPSEEK_API_URL)
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatus::isError, clientResponse -> {
                    log.error("DeepSeek API error: {}", clientResponse.statusCode());
                    return Mono.error(new RuntimeException("API call failed with status: " + clientResponse.statusCode()));
                })
                .bodyToMono(DeepSeekResponse.class)
                .block();
            
            if (response != null && 
                response.getChoices() != null && 
                !response.getChoices().isEmpty()) {
                String content = response.getChoices().get(0).getMessage().getContent();
                log.info("Received response from DeepSeek API");
                return content;
            }
            
            throw new RuntimeException("Empty response from DeepSeek API");
            
        } catch (Exception e) {
            log.error("Error calling DeepSeek API", e);
            throw new RuntimeException("Failed to call DeepSeek API: " + e.getMessage(), e);
        }
    }
    
    /**
     * 多轮对话
     */
    public String multiTurnChat(List<DeepSeekRequest.Message> messages) {
        try {
            DeepSeekRequest request = new DeepSeekRequest();
            request.setMessages(messages);
            request.setTemperature(0.7);
            request.setMax_tokens(2048);
            
            DeepSeekResponse response = webClient.post()
                .uri(DEEPSEEK_API_URL)
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(DeepSeekResponse.class)
                .block();
            
            if (response != null && 
                response.getChoices() != null && 
                !response.getChoices().isEmpty()) {
                return response.getChoices().get(0).getMessage().getContent();
            }
            
            throw new RuntimeException("Empty response from DeepSeek API");
            
        } catch (Exception e) {
            log.error("Error calling DeepSeek API", e);
            throw new RuntimeException("Failed to call DeepSeek API: " + e.getMessage(), e);
        }
    }
}