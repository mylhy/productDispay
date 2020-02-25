package com.pro.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pro.entity.UploadFile;

public interface UploadFileDao extends JpaRepository<UploadFile,Integer>{
    
}