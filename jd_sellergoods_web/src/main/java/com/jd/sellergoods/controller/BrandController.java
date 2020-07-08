package com.jd.sellergoods.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jd.pojo.TbBrand;
import com.jd.sellergoods.service.BrandService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("sellerGoods")
public class BrandController {
   @Reference
   private BrandService brandService;

   @RequestMapping("show")
   public List<TbBrand> getAllBrand(){
      List<TbBrand> allBrand = brandService.getAllBrand();
      return allBrand;
   }


}
