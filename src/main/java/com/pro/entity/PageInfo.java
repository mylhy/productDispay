package com.pro.entity;

import lombok.Data;

@Data
public class PageInfo {
	// 当前页
	private int pageNum = 1;
	// 每页的显示
	private int pageSize = 10;
	// 总记录数
	private int totalResult = 0;
	// 总页数
	private int totalPage = 0;

	public int getTotalPage() {
		
		if (totalResult == 0) {
			this.totalPage = 1;
		} else {
			this.totalPage = (totalResult / pageSize); // 总共几页
			if ((totalResult % pageSize) != 0)
				this.totalPage = this.totalPage + 1;
		}
		return totalPage;
	}
	
	

}
