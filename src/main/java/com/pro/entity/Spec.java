package com.pro.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
/**
 * 规格
 * @author Administrator
 *
 */
@Entity
@Data
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class Spec implements Serializable  {
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	private String name;
    private Timestamp createDate;
    private Integer createUser;
    private Timestamp updateDate;
    private Integer updateUser;

	private int isDelete;
    
    //临时变量，存放value
    @Transient
    private List<SpecValue> specValues;
}
