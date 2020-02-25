package com.pro.service;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pro.dao.UploadFileDao;
import com.pro.entity.UploadFile;

@Service
public class UploadFileService {
	@Autowired
	private UploadFileDao uploadFileDao;
	
	public UploadFile add(UploadFile uploadFile) {
		uploadFile.setCreateDate(new Timestamp(System.currentTimeMillis()));
		return uploadFileDao.save(uploadFile);
	}
	
}
