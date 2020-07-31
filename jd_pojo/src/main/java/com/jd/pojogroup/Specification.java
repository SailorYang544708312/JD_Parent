package com.jd.pojogroup;

import com.jd.pojo.TbSpecification;
import com.jd.pojo.TbSpecificationOption;

import java.io.Serializable;
import java.util.List;

/**
 * 规格和规格选项的组合实体类
 * @author 毛球
 */
public class Specification implements Serializable {
   //规格
   private TbSpecification specification;
   //规格选项的集合
   private List<TbSpecificationOption> specificationOptionList;

   public TbSpecification getSpecification() {
      return specification;
   }

   public void setSpecification(TbSpecification specification) {
      this.specification = specification;
   }

   public List<TbSpecificationOption> getSpecificationOptionList() {
      return specificationOptionList;
   }

   public void setSpecificationOptionList(List<TbSpecificationOption> specificationOptionList) {
      this.specificationOptionList = specificationOptionList;
   }
}
