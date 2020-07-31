package com.jd.sellergoods.service;

import com.jd.common.pojo.PageResult;
import com.jd.pojo.TbBrand;

import java.util.List;
import java.util.Map;

/**
 * 品牌服务
 */
public interface BrandService {
   /**
    * 查询所有的品牌
    * @return
    */
   List<TbBrand> getAllBrand();

   /**
    * 分页（无条件查询）
    * @param pageNum
    * @param pageSize
    * @return
    */
   PageResult findPage(Integer pageNum, Integer pageSize);

   /**
    * 根据条件查询(方法重载)
    * @param tbBrand
    * @param pageNum
    * @param pageSize
    * @return
    */
   PageResult findPage(TbBrand tbBrand,Integer pageNum, Integer pageSize);

   /**
    * 添加品牌
    * @param tbBrand
    */
   void addBrand(TbBrand tbBrand);

   /**
    * 根据id查询对象
    * @param id
    * @return
    */
   TbBrand findOne(Long id);

   /**
    * 修改
    * @param tbBrand
    */
   void updateBrand(TbBrand tbBrand);

   /**
    * 删除
    * @param ids
    */
   void deleteById(Long[] ids);

   /**
    * 为select2准备的 查询品牌 因为select2格式指定为 {data:[{id:1,text:'xx'},{id:2,text:'xxx'}]}
    * @return
    */
   List<Map> selectBrandList();
}
