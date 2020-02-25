package com.pro.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.pro.entity.Product;
import com.pro.entity.Spec;

public interface SpecDao extends JpaRepository<Spec,Integer>,JpaSpecificationExecutor<Spec>{

	List<Spec> findByIsDelete(int i);
    
}