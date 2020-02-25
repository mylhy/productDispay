package com.pro.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pro.entity.User;
import com.pro.service.UserService;

@RestController
@RequestMapping("")
public class LoginController {
	
	@Resource
	private UserService userService;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Map<String, Object> login(String login_name, String login_password,HttpServletRequest request) {
		System.out.println("用户登录,name="+login_name+",password="+login_password);
		Map<String, Object> resultMap=new HashMap<String, Object>();
		String msg = "登录失败";
		String url = "";
		int code = 0;//0表示不通过，1表示表示
		
		//在service处理业务
		User user=userService.login(login_name,login_password);
		if(user!=null) {
			code = 1;
			msg = "登录成功";
			url = "index";
			request.getSession().setAttribute("user", user);
		}

		resultMap.put("code", code);
		resultMap.put("msg", msg);
		resultMap.put("url", url);
		
		return resultMap;
	}
	
}
