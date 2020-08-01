package com.jd.shop.controller;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.jd.common.pojo.JdResult;
import com.jd.common.pojo.PageResult;
import com.jd.pojo.TbSeller;
import com.jd.sellergoods.service.SellerService;

/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/seller")
public class SellerController {

	@Reference
	private SellerService sellerService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbSeller> findAll(){			
		return sellerService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult  findPage(int page,int rows){			
		return sellerService.findPage(page, rows);
	}
	
	/**
	 * 增加
	 * @param seller
	 * @return
	 */
	@RequestMapping("/add")
	public JdResult add(@RequestBody TbSeller seller){
		//BCrypt加密（可以在service工程中去添加springSecurity的核心包，在service工程中写加密程序）
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		//对seller的password进行加密
		String password = passwordEncoder.encode(seller.getPassword());
		//赋值加密后的密码
		seller.setPassword(password);
		try {
			sellerService.add(seller);
			return new JdResult(true, "增加成功",null);
		} catch (Exception e) {
			e.printStackTrace();
			return new JdResult(false, "增加失败",null);
		}
	}
	
	/**
	 * 修改
	 * @param seller
	 * @return
	 */
	@RequestMapping("/update")
	public JdResult update(@RequestBody TbSeller seller){
		try {
			sellerService.update(seller);
			return new JdResult(true, "修改成功",null);
		} catch (Exception e) {
			e.printStackTrace();
			return new JdResult(false, "修改失败",null);
		}
	}	
	
	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	@RequestMapping("/findOne")
	public TbSeller findOne(String id){
		return sellerService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public JdResult delete(String [] ids){
		try {
			sellerService.delete(ids);
			return new JdResult(true, "删除成功" ,null); 
		} catch (Exception e) {
			e.printStackTrace();
			return new JdResult(false, "删除失败" ,null);
		}
	}
	
		/**
	 * 查询+分页
	 * @param
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/search")
	public PageResult search(@RequestBody TbSeller seller, int page, int rows  ){
		return sellerService.findPage(seller, page, rows);		
	}
	
}
