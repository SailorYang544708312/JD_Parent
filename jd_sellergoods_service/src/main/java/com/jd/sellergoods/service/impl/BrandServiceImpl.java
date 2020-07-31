package com.jd.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jd.common.pojo.PageResult;
import com.jd.mapper.TbBrandMapper;
import com.jd.pojo.TbBrand;
import com.jd.pojo.TbBrandExample;
import com.jd.sellergoods.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Service
public class BrandServiceImpl implements BrandService {

   @Autowired
   private TbBrandMapper tbBrandMapper;

   @Override
   public List<TbBrand> getAllBrand() {
      return tbBrandMapper.selectByExample(null);
   }

   @Override
   public PageResult findPage(Integer pageNum, Integer pageSize) {
      PageHelper.startPage(pageNum,pageSize);
      Page<TbBrand> page = (Page<TbBrand>)tbBrandMapper.selectByExample(null);
      return new PageResult(page.getTotal(),page.getResult());
   }

   @Override
   public PageResult findPage(TbBrand tbBrand, Integer pageNum, Integer pageSize) {
      PageHelper.startPage(pageNum,pageSize);
      TbBrandExample example = new TbBrandExample();
      TbBrandExample.Criteria criteria = example.createCriteria();
      if (tbBrand != null){
         if (tbBrand.getName() != null && tbBrand.getName().length()>0){
            criteria.andNameLike("%"+tbBrand.getName()+"%");
         }
         if (tbBrand.getFirstChar() != null && tbBrand.getFirstChar().length()>0){
            criteria.andFirstCharEqualTo(tbBrand.getFirstChar());
         }
      }
      Page<TbBrand> page = (Page<TbBrand>) tbBrandMapper.selectByExample(example);
      return new PageResult(page.getTotal(),page.getResult());
   }

   @Override
   public void addBrand(TbBrand tbBrand) {
      tbBrandMapper.insertSelective(tbBrand);
   }

   @Override
   public TbBrand findOne(Long id) {
      return tbBrandMapper.selectByPrimaryKey(id);
   }

   @Override
   public void updateBrand(TbBrand tbBrand) {
      tbBrandMapper.updateByPrimaryKeySelective(tbBrand);
   }

   @Override
   public void deleteById(Long[] ids) {
      for (Long id : ids) {
         tbBrandMapper.deleteByPrimaryKey(id);
      }
   }

   /**
    * 为select2准备的 查询品牌 因为select2格式指定为
    * {data:[{id:1,text:'xx'},{id:2,text:'xxx'}]}
    * @return
    */
   @Override
   public List<Map> selectBrandList() {
      return tbBrandMapper.selectBrandList();
   }

}
