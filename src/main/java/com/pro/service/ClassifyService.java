package com.pro.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.pro.dao.ClassifyDao;
import com.pro.entity.Classify;
import com.pro.entity.PageInfo;
import com.pro.entity.User;

@Service
public class ClassifyService {
	@Autowired
	private ClassifyDao classifyDao;
	
	public List<Classify> findAll(Map<String, String> queryMap){
		
		//先查询父类，再查询没有父类的，再通过遍历来添加到集合里面
		Specification<Classify> specification = new Specification<Classify>() {
			@Override
			public Predicate toPredicate(Root<Classify> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				//所有的断言 及条件
				List<Predicate> predicates = new ArrayList<>();
				//模糊搜索 name
				if(StringUtils.isNotBlank(queryMap.get("query_name"))) {
                    Predicate predicate = cb.like(root.get("name"), "%"+queryMap.get("query_name")+"%");
                    predicates.add(predicate);
                }
				//过滤有父类的
				Predicate p1 = cb.isNull(root.get("parentId"));
				predicates.add(p1);
				
				//过滤已删除的
				Predicate p2 = cb.equal(root.get("isDelete"), 0);
				predicates.add(p2);
				
				return cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
		
		
		List<Classify> resultList=new ArrayList<Classify>();

		List<Order> sortList=new ArrayList<Order>();
		sortList.add(Sort.Order.asc("sort"));
		sortList.add(Sort.Order.desc("createDate"));
		
		List<Classify> lists=classifyDao.findAll(specification,Sort.by(sortList));
		
		for (int i = 0; i < lists.size(); i++) {
			Classify classify=lists.get(i);
			Specification<Classify> specification2 = new Specification<Classify>() {
				@Override
				public Predicate toPredicate(Root<Classify> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					List<Predicate> predicates = new ArrayList<>();
					if(StringUtils.isNotBlank(queryMap.get("query_name"))) {
	                    Predicate predicate = cb.like(root.get("name"), "%"+queryMap.get("query_name")+"%");
	                    predicates.add(predicate);
	                }
					Predicate p1 = cb.equal(root.get("parentId").get("id"),classify.getId());
					predicates.add(p1);
					Predicate p2 = cb.equal(root.get("isDelete"), 0);
					predicates.add(p2);
					return cb.and(predicates.toArray(new Predicate[predicates.size()]));
				}
			};
			
			resultList.add(classify);
			List<Classify> secondList=classifyDao.findAll(specification2,Sort.by(sortList));
			for (Classify classify2 : secondList) {
				resultList.add(classify2);
			}
		}
		return resultList;
	}
	
	public Classify add(Classify classify,User u) {
		if(classify.getParentId().getId()==null) {
			classify.setParentId(null);
		}
		if(classify.getUploadFile().getId()==null) {
			classify.setUploadFile(null);
		}
		classify.setCreateDate(new Timestamp(System.currentTimeMillis()));
		classify.setCreateUser(u.getId());
		return classifyDao.save(classify);
	}
	/**
	 * 修改
	 * @param classify
	 * @param u
	 * @return
	 */
	public Classify update(Classify classify,User u) {
		Classify oldClassify=classifyDao.getOne(classify.getId());

		if(classify.getParentId() == null || classify.getParentId().getId() == null) {
			classify.setParentId(null);
		}
		
		if(classify.getUploadFile() == null || classify.getUploadFile().getId() == null) {
			classify.setUploadFile(null);
		}
		classify.setCreateDate(oldClassify.getCreateDate());
		classify.setCreateUser(oldClassify.getCreateUser());
		classify.setUpdateDate(new Timestamp(System.currentTimeMillis()));
		classify.setUpdateUser(u.getId());
		return classifyDao.save(classify);
	}

	public List<Classify> findParentId() {
		return classifyDao.findByParentIdIsNull();
	}

	public Classify getId(Integer id) {
		return classifyDao.getOne(id);
	}

	public Classify delete(String id,User u) {
		Classify c=classifyDao.getOne(Integer.valueOf(id));
		if(c != null) {
			c.setIsDelete(-1);
			c.setUpdateDate(new Timestamp(System.currentTimeMillis()));
			c.setUpdateUser(u.getId());
			classifyDao.save(c);
			return c;
		}
		return null;
	}
}
