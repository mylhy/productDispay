package com.pro.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.pro.entity.Classify;
//JpaSpecificationExecutor 用于分页和过滤查询
public interface ClassifyDao extends JpaRepository<Classify,Integer>,JpaSpecificationExecutor<Classify>{
	//查询父类
	List<Classify> findByParentIdIsNull();

	List<Classify> findByparentIdId(Integer id);

	List<Classify> findByParentIdIsNullOrderBySortDesc();
    
}