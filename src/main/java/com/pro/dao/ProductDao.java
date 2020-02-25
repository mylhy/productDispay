package com.pro.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.pro.entity.Product;

public interface ProductDao extends JpaRepository<Product,Integer>,JpaSpecificationExecutor<Product>{
    
}