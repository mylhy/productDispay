package com.pro.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.pro.dao.ProductDao;
import com.pro.dao.ProductFileDao;
import com.pro.dao.ProductSpecDao;
import com.pro.entity.PageInfo;
import com.pro.entity.Product;
import com.pro.entity.ProductFile;
import com.pro.entity.ProductSpec;
import com.pro.entity.Spec;
import com.pro.entity.UploadFile;
import com.pro.entity.User;

@Service
public class ProductService {
	
	@Autowired
	private ProductDao productDao;
	@Autowired
	private ProductFileDao productFileDao;
	@Autowired
	private ProductSpecDao productSpecDao;
	
	public List<Product> findAll(Map<String, String> queryMap,PageInfo pageInfo){

		
		Specification<Product> specification = new Specification<Product>() {
			@Override
			public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				//所有的断言 及条件
				List<Predicate> predicates = new ArrayList<>();
				//模糊搜索 name
				if(StringUtils.isNotBlank(queryMap.get("query_name"))) {
                    Predicate predicate = cb.like(root.get("name"), "%"+queryMap.get("query_name")+"%");
                    predicates.add(predicate);
                }

				//过滤已删除的
				Predicate p = cb.equal(root.get("isDelete"), 0);
				predicates.add(p);
				
				return cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};

		List<Order> sortList=new ArrayList<Order>();
		sortList.add(Sort.Order.asc("sort"));
		sortList.add(Sort.Order.desc("createDate"));
		
		Pageable pageable = PageRequest.of(pageInfo.getPageNum()-1, pageInfo.getPageSize(), Sort.by(sortList));
	    
		Page<Product> pageInfoList = productDao.findAll(specification, pageable);
		
		for (Product product : pageInfoList) {
			product.setProductFiles(productFileDao.findByProductId(product.getId()));
			product.setSpecLists(productSpecDao.findByProductId(product.getId()));
			
		}
		
		return pageInfoList.getContent();
	
	}

	public Product add(Product product,String[] productFiles,String[] specs, User u) {
		if(product.getClassify().getId()==null) {
			product.setClassify(null);
		}
		product.setCreateUser(u.getId());
		product.setCreateDate(new Timestamp(System.currentTimeMillis()));
		product=productDao.save(product);
		if(productFiles != null && productFiles.length>0) {
			addProductFile(product, productFiles);
		}
		if(specs != null && specs.length>0) {
			addProductSpec(product, specs);
		}
		return product;
	}
	

	public Product delete(Integer id, User u) {
		Product product=productDao.getOne(id);
		product.setIsDelete(-1);
		product.setUpdateUser(u.getId());
		product.setUpdateDate(new Timestamp(System.currentTimeMillis()));
		
		return productDao.save(product);
	}
	
	/**
	 * 添加产品规格
	 * @param product
	 * @param specs
	 */
	private void addProductSpec(Product product, String[] specs) {
		for (int i = 0; i < specs.length; i++) {
			String string = specs[i];
			Spec s=new Spec();
			s.setId(Integer.valueOf(string));
			ProductSpec ps=new ProductSpec();
			ps.setProduct(product);
			ps.setSpec(s);
			ps.setCreateUser(product.getCreateUser());
			ps.setCreateDate(new Timestamp(System.currentTimeMillis()));
			productSpecDao.save(ps);
		}
	}

	/**
	 * 添加产品图片
	 */
	private void addProductFile(Product product,String[] productFiles) {
		for (int i = 0; i < productFiles.length; i++) {
			String string = productFiles[i];//图片路径
			ProductFile pf=new ProductFile();
			pf.setProduct(product);
			pf.setUploadFile(new UploadFile(Integer.valueOf(string)));
			pf.setCreateUser(product.getCreateUser());
			pf.setCreateDate(new Timestamp(System.currentTimeMillis()));
			productFileDao.save(pf);
		}
	}


}
