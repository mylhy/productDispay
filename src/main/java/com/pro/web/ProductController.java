package com.pro.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pro.entity.Classify;
import com.pro.entity.PageInfo;
import com.pro.entity.Product;
import com.pro.entity.Spec;
import com.pro.entity.User;
import com.pro.service.ClassifyService;
import com.pro.service.ProductService;
import com.pro.service.SpecService;
import com.pro.util.BaseUtils;

@Controller
@RequestMapping("product")
public class ProductController {
	
	@Autowired//自动注入request
    private HttpServletRequest request;

	@Autowired
	private ClassifyService classifyService;
	@Autowired
	private ProductService productService;
	
	@Autowired
	private SpecService specService;
	
	/**
	 * 首页，加载数据，分页
	 * @param model
	 * @param request
	 * @param pageInfo
	 * @return
	 */
	@RequestMapping("/index")
	public String productList(Model model,HttpServletRequest request,PageInfo pageInfo) {
		//获取模糊条件
    	Map<String, String> queryMap=BaseUtils.getRequestParam(request, "query_");
		List<Product> productList = productService.findAll(queryMap, pageInfo);
		
		model.addAttribute("productList",productList);
		model.addAttribute("pageInfo",pageInfo);
		
		return "product/index";
	}
	
	@RequestMapping("toEdit")
	public String toEdit(String id,Model model) {
		String editType="添加产品";
		if(StringUtils.isNotBlank(id)) {
			editType="修改产品";
			model.addAttribute("id",id);
		}
		//查询所有分类
		List<Classify> classifyList = classifyService.findAll(new HashMap<String, String>());
		
		//查询所有规格
		List<Spec> specList = specService.findAll();

		model.addAttribute("specList",specList);
		model.addAttribute("classifyList",classifyList);
		model.addAttribute("editType",editType);
		return "product/edit";
	}
	
	@RequestMapping("/edit")
	public String add(Product product,String[] productFiles,String[] specs,RedirectAttributes attr) {

		User u=(User) request.getSession().getAttribute("user");
		if(product.getId()!=null) {
			product=productService.add(product,productFiles,specs,u);
			attr.addFlashAttribute("msg", product==null?"修改成功":"修改失败");
		}else {
			product=productService.add(product,productFiles,specs,u);
			attr.addFlashAttribute("msg", product==null?"添加成功":"添加失败");
		}
		
		return "redirect:/product/index";
	}
	 //删除
    @RequestMapping("/delete")
    public String delete(String id,RedirectAttributes attr) {
    	User u = (User) request.getSession().getAttribute("user");
    	if(BaseUtils.isInteger(id)) {
    		Product product = productService.delete(Integer.valueOf(id),u);
    		attr.addFlashAttribute("msg", product == null? "删除失败": "删除成功");
    	}

		return "redirect:/product/index";
    }
}
