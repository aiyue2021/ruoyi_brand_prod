package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.Brand;
import com.ruoyi.system.domain.Brand;

import java.util.List;

/**
 * 品牌 数据层
 * 
 * @author ruoyi
 */
public interface BrandMapper
{
    /**
     * 查询品牌信息
     * 
     * @param brand 品牌信息
     * @return 品牌信息
     */
    public Brand selectBrand(Brand brand);

    /**
     * 通过ID查询配置
     * 
     * @param brandId 参数ID
     * @return 品牌信息
     */
    public Brand selectBrandById(String brandId);

    /**
     * 查询品牌列表
     * 
     * @param brand 品牌信息
     * @return 品牌集合
     */
    public List<Brand> selectBrandList(Brand brand);

    /**
     * 新增品牌
     * 
     * @param brand 品牌信息
     * @return 结果
     */
    public int insertBrand(Brand brand);

    /**
     * 修改品牌
     * 
     * @param brand 品牌信息
     * @return 结果
     */
    public int updateBrand(Brand brand);

    /**
     * 删除品牌
     *
     * @param brandId 参数主键
     * @return 结果
     */
    public int deleteBrandById(String brandId);

    /**
     * 批量删除品牌
     * 
     * @param brandIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteBrandByIds(String[] brandIds);
}