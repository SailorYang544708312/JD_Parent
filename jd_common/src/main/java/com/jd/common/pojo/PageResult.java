package com.jd.common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 用来封装分页结果对象
 */
public class PageResult implements Serializable {
   private Long total;  //总记录条数
   private List rows;   //当前一页信息的数据

   public PageResult() {
   }

   public PageResult(Long total, List rows) {
      this.total = total;
      this.rows = rows;
   }

   public Long getTotal() {
      return total;
   }

   public void setTotal(Long total) {
      this.total = total;
   }

   public List getRows() {
      return rows;
   }

   public void setRows(List rows) {
      this.rows = rows;
   }
}
