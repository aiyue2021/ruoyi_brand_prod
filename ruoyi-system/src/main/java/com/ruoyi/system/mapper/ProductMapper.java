package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.Product;

import java.util.List;

/**
 * 产品 数据层
 * 
 * @author ruoyi
 */
public interface ProductMapper
{
    /**
     * 查询产品信息
     * 
     * @param product 产品信息
     * @return 产品信息
     */
    public Product selectProduct(Product product);

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
     * @param products 产品信息
     * @return 结果
     */
    public int insertProducts(List<Product> products);
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
    public int deleteProductByIds(String[] productIds);
}