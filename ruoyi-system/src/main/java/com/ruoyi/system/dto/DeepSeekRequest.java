package com.ruoyi.system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeepSeekRequest {
    private String model = "deepseek-chat";
    private List<Message> messages;
    private double temperature = 0.7;
    private int max_tokens = 2048;
    private boolean stream = false;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Message {
        private String role;
        private String content;
        
        public static Message userMessage(String content) {
            return new Message("user", content);
        }
        
        public static Message systemMessage(String content) {
            return new Message("system", content);
        }
        
        public static Message assistantMessage(String content) {
            return new Message("assistant", content);
        }
    }
}

