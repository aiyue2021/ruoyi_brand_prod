package com.ruoyi.web.controller.system;

import com.ruoyi.framework.web.service.DeepSeekService;
import com.ruoyi.system.dto.DeepSeekRequest;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@Validated
public class AIController {
    
    private final DeepSeekService deepSeekService;
    
    public AIController(DeepSeekService deepSeekService) {
        this.deepSeekService = deepSeekService;
    }
    
    @PostMapping("/chat")
    public ResponseEntity<Map<String, Object>> chat(String content) {
        try {
            String prompt = "提取该新建文本文档中的电商产品数据涵盖品牌、产品、价格、类别及热卖指标。输出格式按如下样例的json[\n" +
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
                    "  },]";
            String response = deepSeekService.chat(content, prompt);
            Map<String, Object> objectObjectHashMap = new HashMap<>();
            objectObjectHashMap.put("success", true);
            HashMap<Object, Object> responseHashMap = new HashMap<>();
            responseHashMap.put("response", response);
            objectObjectHashMap.put("data", responseHashMap);
            objectObjectHashMap.put("message", "Success");

            return ResponseEntity.ok(objectObjectHashMap);
        } catch (Exception e) {
            Map<String, Object> objectObjectHashMap = new HashMap<>();
            objectObjectHashMap.put("success", false);
            objectObjectHashMap.put("data", null);
            objectObjectHashMap.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(objectObjectHashMap);
        }
    }
    
    @PostMapping("/chat/multi-turn")
    public ResponseEntity<Map<String, Object>> multiTurnChat(@RequestBody @Valid MultiTurnChatRequest request) {
        try {
            String response = deepSeekService.multiTurnChat(request.getMessages());
            Map<String, Object> objectObjectHashMap = new HashMap<>();
            objectObjectHashMap.put("success", true);
            HashMap<Object, Object> responseHashMap = new HashMap<>();
            responseHashMap.put("response", response);
            objectObjectHashMap.put("data", responseHashMap);
            objectObjectHashMap.put("message", "Success");

            return ResponseEntity.ok(objectObjectHashMap);
        } catch (Exception e) {
            Map<String, Object> objectObjectHashMap = new HashMap<>();
            objectObjectHashMap.put("success", false);
            objectObjectHashMap.put("data", null);
            objectObjectHashMap.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(objectObjectHashMap);
        }
    }
    
    // 请求 DTO
    @Data
    public static class ChatRequest {
        @NotBlank(message = "消息不能为空")
        private String message;
        
        private String systemPrompt;
    }
    
    @Data
    public static class MultiTurnChatRequest {
        @NotEmpty(message = "消息列表不能为空")
        private List<DeepSeekRequest.Message> messages;
    }
}