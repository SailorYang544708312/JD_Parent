package com.jd.sellergoods.service.impl;
import java.util.List;
import java.util.Map;

import com.jd.mapper.TbSpecificationOptionMapper;
import com.jd.pojo.TbSpecificationOption;
import com.jd.pojo.TbSpecificationOptionExample;
import com.jd.pojogroup.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jd.mapper.TbSpecificationMapper;
import com.jd.pojo.TbSpecification;
import com.jd.pojo.TbSpecificationExample;
import com.jd.pojo.TbSpecificationExample.Criteria;
import com.jd.sellergoods.service.SpecificationService;
import com.jd.common.pojo.PageResult;

/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class SpecificationServiceImpl implements SpecificationService {

	@Autowired
	private TbSpecificationMapper specificationMapper;

	@Autowired
	private TbSpecificationOptionMapper specificationOptionMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbSpecification> findAll() {
		return specificationMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbSpecification> page=   (Page<TbSpecification>) specificationMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(Specification specification) {
		//1.添加规格
		specificationMapper.insert(specification.getSpecification());
		//2.规格选项
		for (TbSpecificationOption tso : specification.getSpecificationOptionList()) {
			//在specificationMapper.xml 里面的inset方法中已经设置新增会自动返回id值所以这里可以获得id
			tso.setSpecId(specification.getSpecification().getId());
			specificationOptionMapper.insert(tso);
		}
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(Specification specification){
		//修改规格对象
		specificationMapper.updateByPrimaryKey(specification.getSpecification());
		//修改规格选项(每次删除一个选项或者新增一个都是修改 稍有点麻烦)
		//思路方案:先删除原有所有的选项，再来新增新的选项
		//根据规格id删除规格选项
		TbSpecificationOptionExample example = new TbSpecificationOptionExample();
		TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
		criteria.andSpecIdEqualTo(specification.getSpecification().getId());
		specificationOptionMapper.deleteByExample(example);
		//根据规格id循环添加规格选项
		for (TbSpecificationOption tso : specification.getSpecificationOptionList()) {
			tso.setSpecId(specification.getSpecification().getId());
			specificationOptionMapper.insert(tso);
		}
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public Specification findOne(Long id){
		Specification spec = new Specification();
		//根据规格id查询到规格对象
		spec.setSpecification(specificationMapper.selectByPrimaryKey(id));
		//根据规格id查询到规格选项的集合
		TbSpecificationOptionExample example = new TbSpecificationOptionExample();
		TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
		criteria.andSpecIdEqualTo(id);
		List<TbSpecificationOption> list = specificationOptionMapper.selectByExample(example);
		spec.setSpecificationOptionList(list);
		return spec;
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			//删除规格
			specificationMapper.deleteByPrimaryKey(id);
			//删除规格选项
			TbSpecificationOptionExample example = new TbSpecificationOptionExample();
			TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
			criteria.andSpecIdEqualTo(id);
			specificationOptionMapper.deleteByExample(example);
		}		
	}
	
	
	@Override
	public PageResult findPage(TbSpecification specification, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbSpecificationExample example=new TbSpecificationExample();
		Criteria criteria = example.createCriteria();
		
		if(specification!=null){			
						if(specification.getSpecName()!=null && specification.getSpecName().length()>0){
				criteria.andSpecNameLike("%"+specification.getSpecName()+"%");
			}
	
		}
		
		Page<TbSpecification> page= (Page<TbSpecification>)specificationMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}

	@Override
	public List<Map> selectSpecificationList() {
		return specificationMapper.selectSpecificationList();
	}

}
