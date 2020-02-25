package com.pro.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.pro.dao.SpecDao;
import com.pro.dao.SpecValueDao;
import com.pro.entity.PageInfo;
import com.pro.entity.Product;
import com.pro.entity.Spec;
import com.pro.entity.SpecValue;
import com.pro.entity.User;

@Service
public class SpecService {
	@Autowired
	private SpecDao specDao;
	
	@Autowired
	private SpecValueDao specValueDao;

	//查询所有
	public List<Spec> findAll() {
		return specDao.findByIsDelete(0);
	}
	
	
	//分页
	public List<Spec> findAll(PageInfo pageInfo){
		
		Specification<Spec> specification = new Specification<Spec>() {
			@Override
			public Predicate toPredicate(Root<Spec> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				//所有的断言 及条件
				List<Predicate> predicates = new ArrayList<>();
				//过滤已删除的
				Predicate p = cb.equal(root.get("isDelete"), 0);
				predicates.add(p);
				return cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};

		List<Order> sortList=new ArrayList<Order>();
		sortList.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(pageInfo.getPageNum()-1, pageInfo.getPageSize(), Sort.by(sortList));
		
		Page<Spec> pageInfoList = specDao.findAll(specification, pageable);
		List<Spec> specs = pageInfoList.getContent();
		
		for (Spec spec : specs) {
			spec.setSpecValues(specValueDao.findBySpecId(spec.getId()));
		}
		
		pageInfo.setTotalResult((int) specDao.count());
		
		return specs;
		
	}
	
	//添加
	public Spec add(String name,String[] values,User u) {
		Spec s=new Spec();
		s.setName(name);
		s.setCreateDate(new Timestamp(System.currentTimeMillis()));
		s.setCreateUser(u.getId());
		s=specDao.save(s);
		addValue(s.getId(), values, u);
		return s;
	}
	//删除
	public Spec delete(Integer id,User u) {
		Spec s = specDao.getOne(id);
		if(s != null) {
			s.setIsDelete(1);
			s.setUpdateDate(new Timestamp(System.currentTimeMillis()));
			s.setUpdateUser(u.getId());
			return specDao.save(s);
		}
		return null;
	}
	
	//add SpecValue
	public void addValue(Integer id,String[] values,User u) {
		Spec s=new Spec();
		s.setId(id);
		List<SpecValue> svList=new ArrayList<SpecValue>();
		for (int i = 0; i < values.length; i++) {
			String value = values[i];
			SpecValue sv=new SpecValue();
			sv.setSpec(s);
			sv.setSpecValue(value);
			sv.setCreateUser(u.getId());
			sv.setCreateDate(new Timestamp(System.currentTimeMillis()));
			svList.add(sv);
		}
		specValueDao.saveAll(svList);
	}

	public Spec updateValue(Integer id, String name, String[] values, User u) {
		Spec s = specDao.getOne(id);
		if(s == null) {
			return null;
		}
		List<SpecValue> sv=specValueDao.findBySpecId(id);
		specValueDao.deleteAll(sv);
		s.setName(name);
		s.setUpdateDate(new Timestamp(System.currentTimeMillis()));
		s.setUpdateUser(u.getId());
		addValue(s.getId(), values, u);
		return specDao.save(s);
	}

	public Spec getSpec(Integer id) {
		Spec s = specDao.getOne(id);
		return s;
	}
	public List<SpecValue> getSpecValueList(Integer sepcId) {
		return specValueDao.findBySpecId(sepcId);
	}

	
}
