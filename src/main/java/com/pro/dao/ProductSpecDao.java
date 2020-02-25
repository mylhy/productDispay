package com.pro.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pro.entity.ProductSpec;

public interface ProductSpecDao extends JpaRepository<ProductSpec,Integer>{
    
}