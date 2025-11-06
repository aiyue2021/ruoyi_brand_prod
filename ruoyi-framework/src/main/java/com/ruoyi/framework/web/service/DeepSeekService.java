package com.ruoyi.framework.web.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.system.domain.Brand;
import com.ruoyi.system.dto.DeepSeekRequest;
import com.ruoyi.system.dto.DeepSeekResponse;
import com.ruoyi.system.service.IBrandService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@Slf4j
public class DeepSeekService {
    
    private static final String DEEPSEEK_API_URL = "https://api.deepseek.com/chat/completions";
    
    @Value("${deepseek.api.key:}")
    private String apiKey;

    @Autowired
    private WebClient webClient;

    private String escapeJson(String text) {
        return text.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
    /**
     * 非阻塞版本的chat方法
     */
    public String chat(String prompt) throws IOException {
        // 构建请求体
        String requestBody = String.format(
                "{\"model\":\"deepseek-chat\",\"messages\":[{\"role\":\"user\",\"content\":\"%s\"}],\"temperature\":0.7}",
                escapeJson(prompt + ",提取该新建文本文档中的电商产品数据涵盖品牌、产品、价格、类别及热卖指标。输出格式按如下格式且取到的所有产品完整样例的json[\n" +
                        "  {\n" +
                        "    \"id\": \"temu_601099518345686\",\n" +
                        "    \"score\": 0,\n" +
                        "    \"productId\": \"temu_601099518345686\",\n" +
                        "    \"productUrl\": \"https://www.temu.com/high-waist-straight-camouflage-cargo-jeans-camo-print-medium-stretch-side-flap-pocket-high--denim-pants-womens-denim-jeans-clothing-g-601099518345686.html?&top_gallery_url=https%3A%2F%2Fimg.kwcdn.com%2Fproduct%2FFancyalgo%2FVirtualModelMatting%2Fdfaaca674aa4c5066b06218759efea65.jpg&spec_id=16066&spec_gallery_id=446&refer_page_sn=1114&refer_source=0&freesia_scene=114&_oak_freesia_scene=114&_oak_rec_ext_1=MTQ4NA&spec_ids=16066&refer_page_el_sn=201345&refer_page_name=best_sellers&refer_page_id=1114_1709795820365_opkgwsrccy&_x_enter_scene_type=cate_tab\",\n" +
                        "    \"brand\": \"Temu\",\n" +
                        "    \"productTitle\": \"High Waist Straight Camouflage Cargo Jeans, Camo Print Medium Stretch Side Flap Pocket High Rise Denim Pants, Women's Denim Jeans & Clothing\",\n" +
                        "    \"productAttributes\": [\n" +
                        "      {\n" +
                        "        \"key\": \"shape\",\n" +
                        "        \"value\": [\n" +
                        "          \"high-waisted\",\n" +
                        "          \"straight\",\n" +
                        "          \"cargo\"\n" +
                        "        ]\n" +
                        "      },\n" +
                        "      {\n" +
                        "        \"key\": \"texture\",\n" +
                        "        \"value\": [\n" +
                        "          \"elastic\"\n" +
                        "        ]\n" +
                        "      },\n" +
                        "      {\n" +
                        "        \"key\": \"fabric\",\n" +
                        "        \"value\": [\n" +
                        "          \"denim\"\n" +
                        "        ]\n" +
                        "      },\n" +
                        "      {\n" +
                        "        \"key\": \"size\",\n" +
                        "        \"value\": [\n" +
                        "          \"medium\"\n" +
                        "        ]\n" +
                        "      },\n" +
                        "      {\n" +
                        "        \"key\": \"details\",\n" +
                        "        \"value\": [\n" +
                        "          \"pocket\"\n" +
                        "        ]\n" +
                        "      },\n" +
                        "      {\n" +
                        "        \"key\": \"pattern\",\n" +
                        "        \"value\": [\n" +
                        "          \"camouflage\"\n" +
                        "        ]\n" +
                        "      }\n" +
                        "    ],\n" +
                        "    \"holiday\": \"\",\n" +
                        "    \"normTitle\": \"medium pocket camouflage elastic denim cargo high-waisted straight jeans\",\n" +
                        "    \"productImageUrl\": \"https://img.kwcdn.com/product/Fancyalgo/VirtualModelMatting/dfaaca674aa4c5066b06218759efea65.jpg\",\n" +
                        "    \"productSecondaryCategory\": [\n" +
                        "      \"jeans\"\n" +
                        "    ],\n" +
                        "    \"productPrimaryCategory\": [\n" +
                        "      \"DENIM\"\n" +
                        "    ],\n" +
                        "    \"price\": \"$14.84\",\n" +
                        "    \"numReviews\": 47666,\n" +
                        "    \"rating\": 4.6,\n" +
                        "    \"s3ImageUrl\": \"https://d3uxyztjq8r6f0.cloudfront.net/2024-03-07/temu/temu_601099518345686.jpg\",\n" +
                        "    \"s3ImageUrls\": [],\n" +
                        "    \"numReviews30d\": 0,\n" +
                        "    \"numReviews7d\": 0,\n" +
                        "    \"salesGrowth\": 12.5,\n" +
                        "    \"numColors\": \"\",\n" +
                        "    \"salesRank\": 7,\n" +
                        "    \"salesCount\": 95000,\n" +
                        "    \"salesCountStr\": \"95000+ sold\",\n" +
                        "    \"productDate\": \"2024-03-07\",\n" +
                        "    \"imageUploadStatus\": 2,\n" +
                        "    \"productTrendingTag\": [\n" +
                        "      \"Best Seller\"\n" +
                        "    ],\n" +
                        "    \"salesScore\": 97.59977640164294,\n" +
                        "    \"reviewScore\": 56.13861383710955,\n" +
                        "    \"ratingScore\": 92,\n" +
                        "    \"totalScore\": 84.04147235195434,\n" +
                        "    \"brandPermalink\": \"temu\",\n" +
                        "    \"isStar\": 1\n" +
                        "  },]")
        );

        // 创建HTTP连接
        URL url = new URL(DEEPSEEK_API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Bearer " + apiKey);
        connection.setDoOutput(true);
        connection.setConnectTimeout(30000);
        connection.setReadTimeout(60000);

        // 发送请求
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        // 读取响应
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                return response.toString();
            }
        } else {
            throw new IOException("HTTP error: " + responseCode);
        }
    }

    // 响应类
    @Data
    public static class DeepSeekResponse {
        private List<Choice> choices;

        @Data
        public static class Choice {
            private Message message;
        }

        @Data
        public static class Message {
            private String content;
        }
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