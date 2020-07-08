package com.jd.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.jd.mapper.TbBrandMapper;
import com.jd.pojo.TbBrand;
import com.jd.sellergoods.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

   @Autowired
   private TbBrandMapper tbBrandMapper;

   @Override
   public List<TbBrand> getAllBrand() {
      return tbBrandMapper.selectByExample(null);
   }
}
