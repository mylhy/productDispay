package com.pro.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pro.entity.PageInfo;
import com.pro.entity.Spec;
import com.pro.entity.User;
import com.pro.service.SpecService;
import com.pro.util.BaseUtils;

@Controller
@RequestMapping("spec")
public class SpecController {
	
	@Autowired//自动注入request
    private HttpServletRequest request;
	
	@Autowired
	private SpecService specService;
	
    @RequestMapping("/index")
    public String index(String page,Model model) {
    	PageInfo pageInfo=new PageInfo();
    	if(StringUtils.isNotBlank(page) && BaseUtils.isInteger(page)) {
        	pageInfo.setPageNum(Integer.valueOf(page));
    	}
    	model.addAttribute("specList",specService.findAll(pageInfo));
		model.addAttribute("pageInfo",JSON.toJSON(pageInfo));
    	return "spec/index";
    }

    
    //获取规格对象
	@RequestMapping("/get")
	@ResponseBody
    public Map<String, Object> getSpec(String id) {
		Map<String, Object> res=new HashMap<String, Object>();
    	if(StringUtils.isNotBlank(id) && BaseUtils.isInteger(id)) {
    		Spec s = specService.getSpec(Integer.valueOf(id));
    		res.put("spec", s);
    		res.put("specValue",specService.getSpecValueList(s.getId()));
        	return res;
    	}
		return null;
    }
	
	//添加规格
    @RequestMapping("/add")
	public String add(String id,String name,String[] value,RedirectAttributes attr) {
		User u = (User) request.getSession().getAttribute("user");
		//有id，再次添加，没有id，表现第一次添加
		//首次添加name value 分别在spec specvalue各添加一个值
		Spec s = null;
		if(StringUtils.isNotBlank(id) && BaseUtils.isInteger(id)) {
			//保存id 在spec value追加值
			s = specService.updateValue(Integer.valueOf(id),name, value, u);
		}else {
			s = specService.add(name,value, u);
		}

		attr.addFlashAttribute("msg", s != null? "添加成功" : "添加失败");
		
		return "redirect:/spec/index";
	}
    
    //删除
    @RequestMapping("/delete")
    public String delete(String id,RedirectAttributes attr) {
    	User u = (User) request.getSession().getAttribute("user");
    	if(BaseUtils.isInteger(id)) {
    		Spec s = specService.delete(Integer.valueOf(id),u);
    		attr.addFlashAttribute("msg", s == null? "删除失败": "删除成功");
    	}

		return "redirect:/spec/index";
    }
    
}
