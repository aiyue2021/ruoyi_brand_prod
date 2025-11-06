package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.Stree;
import com.ruoyi.common.core.domain.Ztree;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.shiro.util.AuthorizationUtils;
import com.ruoyi.system.domain.Brand;
import com.ruoyi.system.domain.Product;
import com.ruoyi.system.service.*;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 产品信息
 * 
 * @author ruoyi
 */
@Controller
@RequestMapping("/system/product")
public class ProductController extends BaseController
{
    private String prefix = "system/product";


    @Autowired
    private IProductService productService;
    @Autowired
    private IBrandService brandService;

    @RequiresPermissions("system:product:view")
    @GetMapping()
    public String product()
    {
        return prefix + "/product";
    }

    @RequiresPermissions("system:product:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Product product)
    {
        startPage();
        List<Product> list = productService.selectProductList(product);
        return getDataTable(list);
    }

    /**
     * 加载品牌列表树
     */
    @RequiresPermissions("system:product:list")
    @GetMapping("/brandTreeData")
    @ResponseBody
    public List<Stree> brandTreeData()
    {
        List<Stree> ztrees = productService.selectBrandTree(new Brand());
        return ztrees;
    }
    
    /**
     * 选择品牌树
     *
     * @param brandId 品牌ID
     */
    @RequiresPermissions("system:product:list")
    @GetMapping("/selectBrandTree/{brandId}")
    public String selectBrandTree(@PathVariable("brandId") String brandId, ModelMap mmap)
    {
        mmap.put("brand", brandService.selectBrandById(brandId));
        return prefix + "/brandTree";
    }


    @Log(title = "产品管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:product:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Product product)
    {
        List<Product> list = productService.selectProductList(product);
        ExcelUtil<Product> util = new ExcelUtil<Product>(Product.class);
        return util.exportExcel(list, "产品数据");
    }

    @RequiresPermissions("system:product:view")
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate()
    {
        ExcelUtil<Product> util = new ExcelUtil<Product>(Product.class);
        return util.importTemplateExcel("产品数据");
    }
    /**
     * 新增产品
     */
    @RequiresPermissions("system:product:add")
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存产品
     */
    @RequiresPermissions("system:product:add")
    @Log(title = "产品管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated Product product)
    {
        return toAjax(productService.insertProduct(product));
    }

    /**
     * 修改产品
     */
    @RequiresPermissions("system:product:edit")
    @GetMapping("/edit/{prodId}")
    public String edit(@PathVariable("prodId") String prodId, ModelMap mmap)
    {
        mmap.put("id", prodId);
        Product product = productService.selectProductById(prodId);
        mmap.put("product", product);
        mmap.put("brand", product.getBrand());
        return prefix + "/edit";
    }

    /**
     * 查询产品详细
     */
    @RequiresPermissions("system:product:list")
    @GetMapping("/view/{prodId}")
    public String view(@PathVariable("prodId") String prodId, ModelMap mmap)
    {
        mmap.put("product", productService.selectProductById(prodId));
        return prefix + "/view";
    }

    /**
     * 修改保存产品
     */
    @RequiresPermissions("system:product:edit")
    @Log(title = "产品管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated Product product)
    {
        return toAjax(productService.updateProduct(product));
    }

    @RequiresPermissions("system:product:remove")
    @Log(title = "产品管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(productService.deleteProductByIds(ids));
    }

}