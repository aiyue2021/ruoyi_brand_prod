package com.ruoyi.framework.web.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.system.domain.Brand;
import com.ruoyi.system.domain.Product;
import com.ruoyi.system.service.IBrandService;
import com.ruoyi.system.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.netty.http.client.PrematureCloseException;
import reactor.util.retry.Retry;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeoutException;

// 数据提取服务类
@Service
@EnableScheduling
public class ProductDataExtractorService {
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private IBrandService brandService;
    @Autowired
    private IProductService productService;
    @Autowired
    private DeepSeekService deepSeekService;

    private WebClient webClient;

    @PostConstruct
    public void init(){
        this.webClient = WebClient.builder().codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(10 * 1024 * 1024)).build();
    }

    @Scheduled(fixedRate = 1000)
    public void scheduledTask() throws InterruptedException {
        List<Brand> brands = brandService.selectBrandList(new Brand());
        for (int i = 0; i < brands.size(); i++) {
            Brand brand = brands.get(i);
            if(brand.getBrandUrl() == null){
                continue;
            }
            //Get请求获取 brand.getBrandUrl() 网页内容 为 字符串
            webClient.get()
                    .uri(brand.getBrandUrl())
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                    .header("Accept-Language", "en-US,en;q=0.5")
                    .header("Accept-Encoding", "gzip, deflate, br")
                    .header("Connection", "keep-alive")
                    .header("Upgrade-Insecure-Requests", "1")
                    .retrieve()
                    .bodyToMono(String.class)
                    .timeout(Duration.ofSeconds(30)) // 30秒超时
                    .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)) // 重试3次，间隔2秒
                    .filter(throwable -> {
                        // 只对网络错误重试
                        return throwable instanceof PrematureCloseException ||
                                throwable instanceof IOException ||
                                throwable instanceof TimeoutException;
                    })
                    .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> {
                        throw new RuntimeException("重试3次后仍然失败: " + brand.getBrandUrl(), retrySignal.failure());
                    }))
                    .subscribe(
                            htmlContent -> {
                                // 成功获取内容
//                                System.out.println(htmlContent);
                                try {
                                    String chat = deepSeekService.chat(htmlContent);
                                    System.out.println(chat);
                                    JSONObject jsonObject = JSONObject.parseObject(chat);
                                    JSONArray choices = jsonObject.getJSONArray("choices");
                                    String chatMsg = choices.getJSONObject(0).getJSONObject("message").getString("content");
                                    String substring = chatMsg.substring(chatMsg.indexOf("["), chatMsg.indexOf("```",chatMsg.indexOf("[")));
                                    List<Product> products = extractProductsFromJson(substring,brand);
                                    productService.insertProducts(products);
                                    System.out.println("成功保存产品数据: " + products.size());
                                } catch (Exception e) {
                                    System.out.println("保存产品数据失败" + e.getMessage());
                                }
                            },
                            error -> {
                                // 错误处理
                                System.err.println("获取品牌URL内容失败: " + brand.getBrandUrl() + ", 错误: " + error.getMessage());
                            }
                    );
        }
        //将html字符串传递给chat(String userMessage)
    }

    /**
     * 从JSON字符串中提取产品数据
     */
    public List<Product> extractProductsFromJson(String jsonString,Brand brand) {
        List<Product> products = new ArrayList<>();

        try {
            JSONArray objects = JSON.parseArray(jsonString);

            for (int i = 0; i < objects.size(); i++) {
                JSONObject jsonObject = objects.getJSONObject(i);
                Product product = extractProductData(jsonObject);
                if (product != null) {
                    product.setId(UUID.randomUUID().toString());
                    product.setBrandId(brand.getId());
                    products.add(product);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return products;
    }

    /**
     * 提取价格（移除$符号）
     */
    private static Float extractPrice(String priceStr) {
        if (priceStr == null || priceStr.isEmpty()) {
            return 0.0f;
        }
        try {
            // 移除$符号并转换为Float
            return Float.parseFloat(priceStr.replace("$", ""));
        } catch (NumberFormatException e) {
            return 0.0f;
        }
    }

    /**
     * 从单个产品数据中提取字段
     */
    private Product extractProductData(JSONObject productData) {
        try {
            Product product = new Product();

            // 提取产品主键
            product.setId((String) productData.get("id"));

            // 提取品牌主键（使用brandPermalink或brand）
            String brandPermalink = (String) productData.get("brandPermalink");
            if (brandPermalink != null && !brandPermalink.isEmpty()) {
                product.setBrandId(brandPermalink);
            } else {
                product.setBrandId((String) productData.get("brand"));
            }

            // 提取产品名称
            product.setProdName((String) productData.get("productTitle"));

            // 提取产品URL
            product.setProdUrl((String) productData.get("productUrl"));

            // 提取产品价格
            String priceStr = (String) productData.get("price");
            product.setProdPrice(extractPrice(priceStr));

            // 提取热销指标（使用totalScore或salesScore）
            Object totalScore = productData.get("totalScore");
            Object salesScore = productData.get("salesScore");
            if (totalScore != null) {
                product.setProdSalesScore(((Number) totalScore).floatValue());
            } else if (salesScore != null) {
                product.setProdSalesScore(((Number) salesScore).floatValue());
            } else {
                // 如果没有评分，使用销售数量作为热销指标
                Object salesCount = productData.get("salesCount");
                if (salesCount != null) {
                    product.setProdSalesScore(((Number) salesCount).floatValue());
                } else {
                    product.setProdSalesScore(0.0f);
                }
            }

            // 提取产品品类（使用主分类）
            List<String> primaryCategory = (List<String>) productData.get("productPrimaryCategory");
            if (primaryCategory != null && !primaryCategory.isEmpty()) {
                product.setProdType(primaryCategory.get(0));
            } else {
                List<String> secondaryCategory = (List<String>) productData.get("productSecondaryCategory");
                if (secondaryCategory != null && !secondaryCategory.isEmpty()) {
                    product.setProdType(secondaryCategory.get(0));
                } else {
                    product.setProdType("Unknown");
                }
            }

            // 提取产品时间
            String productDateStr = (String) productData.get("productDate");
            if (productDateStr != null && !productDateStr.isEmpty()) {
                try {
                    product.setProdDate(dateFormat.parse(productDateStr));
                } catch (ParseException e) {
                    // 如果日期解析失败，使用当前日期
                    product.setProdDate(new Date());
                }
            } else {
                product.setProdDate(new Date());
            }

            return product;

        } catch (Exception e) {
            System.err.println("Error extracting product data: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

}