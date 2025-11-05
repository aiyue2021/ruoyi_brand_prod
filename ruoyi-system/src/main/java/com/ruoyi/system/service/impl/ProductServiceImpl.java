package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.text.Convert;
import com.ruoyi.system.domain.Product;
import com.ruoyi.system.mapper.ProductMapper;
import com.ruoyi.system.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 产品 服务层实现
 * 
 * @author ruoyi
 */
@Service
public class ProductServiceImpl implements IProductService
{

    @Autowired
    private ProductMapper productMapper;

    /**
     * 通过ID查询配置
     * 
     * @param productId 参数ID
     * @return 产品信息
     */
    @Override
    public Product selectProductById(String productId){
        return productMapper.selectProductById(productId);
    }

    /**
     * 查询产品列表
     * 
     * @param product 产品信息
     * @return 产品集合
     */
    @Override
    public List<Product> selectProductList(Product product) {
        return productMapper.selectProductList(product);
    }

    /**
     * 新增产品
     * 
     * @param product 产品信息
     * @return 结果
     */
    @Override
    public int insertProduct(Product product) {
        return productMapper.insertProduct(product);
    }

    /**
     * 修改产品
     * 
     * @param product 产品信息
     * @return 结果
     */
    @Override
    public int updateProduct(Product product){
        return productMapper.updateProduct(product);
    }

    /**
     * 删除产品
     * 
     * @param productId 参数主键
     * @return 结果
     */
    @Override
    public int deleteProductById(String productId) {
        return productMapper.deleteProductById(productId);
    }

    /**
     * 批量删除产品
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteProductByIds(String ids){
        return productMapper.deleteProductByIds(Convert.toStrArray(ids));
    }
}