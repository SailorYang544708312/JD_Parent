package com.jd.pojogroup;

import com.jd.pojo.TbGoods;
import com.jd.pojo.TbGoodsDesc;
import com.jd.pojo.TbItem;

import java.io.Serializable;
import java.util.List;

/**
 * 商品信息实体类
 * 用于添加商品信息 包含数据中的三张表 goods goods_desc item
 * @author 毛球
 */
public class Commodity implements Serializable {

   /**
    * 商品SPU
    */
   private TbGoods goods;

   /**
    * 商品的扩展信息
    */
   private TbGoodsDesc goodsDesc;

   /**
    * 商品SKU列表
    */
   private List<TbItem> itemList;

   public TbGoods getGoods() {
      return goods;
   }

   public void setGoods(TbGoods goods) {
      this.goods = goods;
   }

   public TbGoodsDesc getGoodsDesc() {
      return goodsDesc;
   }

   public void setGoodsDesc(TbGoodsDesc goodsDesc) {
      this.goodsDesc = goodsDesc;
   }

   public List<TbItem> getItemList() {
      return itemList;
   }

   public void setItemList(List<TbItem> itemList) {
      this.itemList = itemList;
   }
}
