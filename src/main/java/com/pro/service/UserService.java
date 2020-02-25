package com.pro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pro.dao.UserDao;
import com.pro.entity.User;
import com.pro.util.BaseUtils;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	
	public User login(String login_name, String password) {
		User user=userDao.findByLoginName(login_name);
		String md_password=BaseUtils.encryptionPassword(password);
		System.out.println(md_password);
		if(user == null || (user != null && !md_password.equals(user.getLoginPassword())) || user.getState() == -1 ) {
			return null;
		}
		return user;
	}
	
	public User findUserLoginName(String loginName) {
		return userDao.findByLoginName(loginName);
	}
	public User findUserId(String uId) {
		try {
			Integer id=Integer.parseInt(uId);
			return userDao.getOne(id);
		} catch (NumberFormatException	 e) {
			e.printStackTrace();
			return null;
		}
	}
}
