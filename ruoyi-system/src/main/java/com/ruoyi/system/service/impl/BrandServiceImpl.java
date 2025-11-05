package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.text.Convert;
import com.ruoyi.system.domain.Brand;
import com.ruoyi.system.mapper.BrandMapper;
import com.ruoyi.system.service.IBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 品牌 服务层实现
 * 
 * @author ruoyi
 */
@Service
public class BrandServiceImpl implements IBrandService
{
    @Autowired
    private BrandMapper brandMapper;

    /**
     * 通过ID查询配置
     * 
     * @param brandId 参数ID
     * @return 品牌信息
     */
    @Override
    public Brand selectBrandById(String brandId) {
        return brandMapper.selectBrandById(brandId);
    }

    /**
     * 查询品牌列表
     * 
     * @param brand 品牌信息
     * @return 品牌集合
     */
    @Override
    public List<Brand> selectBrandList(Brand brand){
        return brandMapper.selectBrandList(brand);
    }

    /**
     * 新增品牌
     * 
     * @param brand 品牌信息
     * @return 结果
     */
    @Override
    public int insertBrand(Brand brand) {
        return brandMapper.insertBrand(brand);
    }

    /**
     * 修改品牌
     * 
     * @param brand 品牌信息
     * @return 结果
     */
    @Override
    public int updateBrand(Brand brand) {
        return brandMapper.updateBrand(brand);
    }

    /**
     * 删除品牌
     *
     * @param brandId 参数主键
     * @return 结果
     */
    @Override
    public int deleteBrandById(String brandId) {
        return brandMapper.deleteBrandById(brandId);
    }

    /**
     * 批量删除品牌
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteBrandByIds(String ids) {
        return brandMapper.deleteBrandByIds(Convert.toStrArray(ids));
    }
}