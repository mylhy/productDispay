package com.pro.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Entity
@Data
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class UploadFile implements Serializable {
	
	
	
	public UploadFile() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UploadFile(Integer id) {
		super();
		this.id = id;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	private String fileUrl;  //路径
	private String fileName;  //文件名
	private String fileType;  //文件类型
	private int isDelete;

    private long fileSize;

    @Column(name="create_date")
    private Timestamp createDate;
    @Column(name="create_user")
    private Integer createUser;
    @Column(name="update_date")
    private Timestamp updateDate;
    @Column(name="update_user")
    private Integer updateUser;
}
