package com.pro.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pro.entity.ProductFile;

public interface ProductFileDao extends JpaRepository<ProductFile,Integer>{

	List<ProductFile> findByProductId(Integer id);
    
}