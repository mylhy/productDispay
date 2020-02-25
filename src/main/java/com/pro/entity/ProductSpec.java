package com.pro.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
/**
 * 产品规格	
 * @author Administrator
 *
 */
@Entity
@Data
public class ProductSpec implements Serializable  {
	private static final long serialVersionUID = 55412451L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	//产品id

    @ManyToOne
    @JoinColumn(name = "product_id")
	private Product product;
	
    @ManyToOne
    @JoinColumn(name = "spec_id")
	private Spec spec;
	
//	private String goodsNo;//编号
//	private double goodsPrice;//价格
//	private double linePrice;//划线价格
//	private int stockNum;//库存数量
//	private int goodsSales;//销量
//	private double goodsWeight;//重量
//	
//	private String specSku;//多规格，用_格开

    private Timestamp createDate;
    private Integer createUser;
    private Timestamp updateDate;
    private Integer updateUser;
}
