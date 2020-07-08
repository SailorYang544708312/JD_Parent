package com.jd.sellergoods.service;

import com.jd.pojo.TbBrand;

import java.util.List;

/**
 * 品牌服务
 */
public interface BrandService {
   /**
    * 查询所有的品牌
    * @return
    */
   List<TbBrand> getAllBrand();
}
