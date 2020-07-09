package com.jd.sellergoods.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jd.common.pojo.JdResult;
import com.jd.common.pojo.PageResult;
import com.jd.pojo.TbBrand;
import com.jd.sellergoods.service.BrandService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("brand")
public class BrandController {
   @Reference
   private BrandService brandService;

   @RequestMapping("show")
   public List<TbBrand> getAllBrand(){
      List<TbBrand> allBrand = brandService.getAllBrand();
      return allBrand;
   }

   /**
    * 分页查询 （angularjs设计好了 当前页面就叫page,每页显示的信息条数就叫rows）
    * @param pageNum
    * @param pageSize
    * @return
    */
   @RequestMapping("findPage")
   public PageResult findPage(@RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
                              @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize){
      return brandService.findPage(pageNum,pageSize);
   }

   @RequestMapping("add")
   public JdResult add(@RequestBody TbBrand tbBrand){
      try {
         brandService.addBrand(tbBrand);
         return JdResult.ok();
      }catch (Exception e){
         e.printStackTrace();
         return new JdResult(false,"添加失败",null);
      }
   }

   /**
    * 根据id获取实体对象
    * @param id
    * @return
    */
   @RequestMapping("findOne")
   public TbBrand findOne(@RequestParam("id")Long id){
      return brandService.findOne(id);
   }

   @RequestMapping("update")
   public JdResult update(@RequestBody TbBrand tbBrand){
      try {
         brandService.updateBrand(tbBrand);
         return JdResult.ok();
      }catch (Exception e){
         return new JdResult(false,"修改失败-名称已存在",null);
      }
   }

   @RequestMapping("delete")
   public JdResult delete(@RequestParam("ids")Long[] ids){
      try {
         brandService.deleteById(ids);
         return JdResult.ok();
      }catch (Exception e){
         return new JdResult(false,"删除失败",null);
      }
   }

   @RequestMapping("search")
   public PageResult search(@RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
                            @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize,
                            @RequestBody TbBrand tbBrand){
      return brandService.findPage(tbBrand,pageNum,pageSize);
   }
}
