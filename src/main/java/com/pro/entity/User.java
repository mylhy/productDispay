package com.pro.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
    @Column(name="login_name",unique =true)
    private String loginName;//登录帐号
    
    @Column(name="login_password")
    private String loginPassword;//登录密码
    
    private String name;//昵称
    
    private byte state;//用户状态,预留字段
    
    @Column(name="create_date")
    private Timestamp createDate;
    @Column(name="create_user")
    private Integer createUser;
    @Column(name="update_date")
    private Timestamp updateDate;
    @Column(name="update_user")
    private Integer updateUser;
    
}