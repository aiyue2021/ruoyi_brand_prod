package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.Stree;
import com.ruoyi.common.core.domain.Ztree;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.system.domain.Brand;
import com.ruoyi.system.domain.Product;

import java.util.List;

/**
 * 产品 服务层
 * 
 * @author ruoyi
 */
public interface IProductService
{
    /**
     * 查询产品管理树
     *
     * @param brand 产品信息
     * @return 所有产品信息
     */
    public List<Stree> selectBrandTree(Brand brand);
    /**
     * 通过ID查询配置
     * 
     * @param productId 参数ID
     * @return 产品信息
     */
    public Product selectProductById(String productId);

    /**
     * 查询产品列表
     * 
     * @param product 产品信息
     * @return 产品集合
     */
    public List<Product> selectProductList(Product product);

    /**
     * 新增产品
     * 
     * @param product 产品信息
     * @return 结果
     */
    public int insertProduct(Product product);

    /**
     * 修改产品
     * 
     * @param product 产品信息
     * @return 结果
     */
    public int updateProduct(Product product);

    /**
     * 删除产品
     * 
     * @param productId 参数主键
     * @return 结果
     */
    public int deleteProductById(String productId);

    /**
     * 批量删除产品
     * 
     * @param productIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteProductByIds(String productIds);
}