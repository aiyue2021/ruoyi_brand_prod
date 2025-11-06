package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.Brand;
import com.ruoyi.system.service.IBrandService;
import com.ruoyi.system.service.IBrandService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 品牌管理
 * 
 * @author ruoyi
 */
@Controller
@RequestMapping("/system/brand")
public class BrandController extends BaseController
{
    private String prefix = "system/brand";

    @Autowired
    private IBrandService brandService;

    @RequiresPermissions("system:brand:view")
    @GetMapping()
    public String brand()
    {
        return prefix + "/brand";
    }

    /**
     * 查询品牌列表
     */
    @RequiresPermissions("system:brand:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Brand brand)
    {
        startPage();
        List<Brand> list = brandService.selectBrandList(brand);
        return getDataTable(list);
    }

    /**
     * 新增品牌
     */
    @RequiresPermissions("system:brand:add")
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存品牌
     */
    @RequiresPermissions("system:brand:add")
    @Log(title = "品牌", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated Brand brand)
    {
        return toAjax(brandService.insertBrand(brand));
    }

    /**
     * 修改品牌
     */
    @RequiresPermissions("system:brand:edit")
    @GetMapping("/edit/{brandId}")
    public String edit(@PathVariable("brandId") String brandId, ModelMap mmap)
    {
        mmap.put("brand", brandService.selectBrandById(brandId));
        return prefix + "/edit";
    }

    /**
     * 修改保存品牌
     */
    @RequiresPermissions("system:brand:edit")
    @Log(title = "品牌", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated Brand brand)
    {
        return toAjax(brandService.updateBrand(brand));
    }

    /**
     * 查询品牌详细
     */
    @RequiresPermissions("system:brand:list")
    @GetMapping("/view/{brandId}")
    public String view(@PathVariable("brandId") String brandId, ModelMap mmap)
    {
        mmap.put("brand", brandService.selectBrandById(brandId));
        return prefix + "/view";
    }

    /**
     * 删除品牌
     */
    @RequiresPermissions("system:brand:remove")
    @Log(title = "品牌", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(brandService.deleteBrandByIds(ids));
    }
}
