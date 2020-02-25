package com.pro.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Entity
@Data
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class SpecValue implements Serializable  {
	private static final long serialVersionUID = 55412451L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	private String specValue;
	
	@ManyToOne
	@JoinColumn(name = "spec_id")
	private Spec spec;

    private Timestamp createDate;
    private Integer createUser;
    private Timestamp updateDate;
    private Integer updateUser;
}
