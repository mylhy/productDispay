package com.pro.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.pro.entity.Product;
import com.pro.entity.SpecValue;

public interface SpecValueDao extends JpaRepository<SpecValue,Integer>,JpaSpecificationExecutor<Product>{

	List<SpecValue> findBySpecId(Integer id);
}