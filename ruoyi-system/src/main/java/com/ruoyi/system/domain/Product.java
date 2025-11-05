package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.util.Date;
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
    private Date prodDate;
    @Excel(name = "备用字段")
    private String tag;
}
