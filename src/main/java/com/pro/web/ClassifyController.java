package com.pro.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pro.entity.Classify;
import com.pro.entity.PageInfo;
import com.pro.entity.User;
import com.pro.service.ClassifyService;
import com.pro.util.BaseUtils;

@Controller
@RequestMapping("classify")
public class ClassifyController {
	
	@Autowired//自动注入request
    private HttpServletRequest request;
	
	@Autowired
	private ClassifyService classifyService;
	
	/**
	 * 首页，加载数据，不分页
	 * @param model
	 * @param request
	 * @param pageInfo
	 * @return
	 */
	@RequestMapping("/index")
	public String classify(Model model,HttpServletRequest request,PageInfo pageInfo) {
    	Map<String, String> query=BaseUtils.getRequestParam(request, "query_");
    	
		List<Classify> list = classifyService.findAll(query);
		model.addAttribute("classifyList", list);
		return "classify/index";
	}
	
	/**
	 * 去到添加页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/toEdit")
	public String toEdit(String id,Model model) {
		String editType="添加分类";
		if(StringUtils.isNotBlank(id)) {
			editType="修改分类";
			model.addAttribute("id",id);
		}
		//父类列表
		List<Classify> list=classifyService.findParentId();
		model.addAttribute("classifyList",list);
		model.addAttribute("editType",editType);
		return "classify/edit";
	}

	/**
	 * 添加或者修改，根据有无ID
	 * @param classify
	 * @return
	 */
	@RequestMapping("/edit")
	public String editClassify(Classify classify,RedirectAttributes attr) {
		String msg="添加失败";
		User u=(User) request.getSession().getAttribute("user");
		if(classify.getId()!=null) {
			classify=classifyService.update(classify, u);
			msg=classify==null?"修改失败":"修改成功";
		}else {
			classify=classifyService.add(classify, u);
			msg=classify==null?"添加失败":"添加成功";
		}
		attr.addFlashAttribute("msg", msg);
		return "redirect:/classify/index";
	}

	/**逻辑删除
	 * @param id
	 * @param attr
	 * @return
	 */
	@RequestMapping("/delete")
	public String deleteClassify(String id,RedirectAttributes attr) {
		String msg="删除失败";
		User u=(User) request.getSession().getAttribute("user");
		Classify classify=classifyService.delete(id,u);
		if(classify != null) {
			msg="删除成功";
		}

		attr.addFlashAttribute("msg", msg);
		return "redirect:/classify/index";
	}

	@RequestMapping("/get")
	@ResponseBody
	public Classify get(String id) {
//		判断id是否正确
		if(BaseUtils.isInteger(id)) {
			Classify c=classifyService.getId(Integer.valueOf(id));
			return c;
		}
		
		return  null;
	}
}
