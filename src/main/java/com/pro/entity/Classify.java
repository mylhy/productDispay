package com.pro.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/**
 * 分类
 * @author galen
 *
 */
@Entity
@Data
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class Classify implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	private String name;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	private Classify parentId;//父类

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "file_id")
	private UploadFile uploadFile;
	
	private int sort;
	private int isDelete;
    private Timestamp createDate;
    private Integer createUser;
    private Timestamp updateDate;
    private Integer updateUser;
    
    @Transient
    private List<Classify> classifyList;//临时变量，子类型集合
	
	
}
