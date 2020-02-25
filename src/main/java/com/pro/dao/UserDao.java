package com.pro.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pro.entity.User;

public interface UserDao extends JpaRepository<User,Integer>{
	
    public User findByLoginName(String loginName);
    
}