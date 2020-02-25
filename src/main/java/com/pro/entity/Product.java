package com.pro.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import lombok.Data;

/**
 * 产品类
 * @author galen
 *
 */
@Entity
@Data
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
//	名称
	private String name;

	@ManyToOne
	@JoinColumn(name = "classify_id")
	private Classify classify;//类别


	@Lob
	private String content;//商品详情
	
	private int status;//状态
	private int isDelete;
	
	private int sort;//排序
	
    private Timestamp createDate;
    private Integer createUser;
    private Timestamp updateDate;
    private Integer updateUser;
	
    

    @Transient
	private List<ProductFile> productFiles;	
    @Transient
	private List<ProductSpec> specLists;
}
