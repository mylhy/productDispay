package com.pro.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pro.entity.ProductSpec;

public interface ProductSpecDao extends JpaRepository<ProductSpec,Integer>{

	List<ProductSpec> findByProductId(Integer id);
    
}