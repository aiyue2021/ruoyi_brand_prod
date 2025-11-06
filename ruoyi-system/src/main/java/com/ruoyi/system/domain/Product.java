package com.ruoyi.system.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excels;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
public class Product {

    @Excel(name = "产品主键")
    private String id;
    @Excel(name = "品牌主键")
    private String brandId;
    @Excel(name = "产品名称")
    private String prodName;
    @Excel(name = "产品Url")
    private String prodUrl;
    @Excel(name = "产品价格")
    private Float prodPrice;
    @Excel(name = "热销指标")
    private Float prodSalesScore;
    @Excel(name = "产品品类")
    private String prodType;
    @Excel(name = "产品时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date prodDate;
    private String tag;
    @Excels({
            @Excel(name = "品牌名称", targetAttr = "brandName", type = Excel.Type.EXPORT),
    })
    private Brand brand;

    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, Object> params;

    public Map<String, Object> getParams()
    {
        if (params == null)
        {
            params = new HashMap<>();
        }
        return params;
    }

    public void setParams(Map<String, Object> params)
    {
        this.params = params;
    }
}
