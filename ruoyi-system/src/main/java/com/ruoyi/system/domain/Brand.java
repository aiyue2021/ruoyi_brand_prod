package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

@Data
public class Brand {

    @Excel(name = "品牌主键")
    private String id;
    @Excel(name = "品牌名称")
    private String brandName;
    @Excel(name = "品牌Url")
    private String brandUrl;
    @Excel(name = "备用字段")
    private String tag;
}
