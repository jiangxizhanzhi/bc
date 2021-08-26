package com.cyberpeace.common.model;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel
public class PageReqBase implements Serializable {

	@ApiModelProperty(name = "per_page", value = "每页大小")
	@Getter
	private Integer perPage = 10;

	@ApiModelProperty(value = "页号，从1开始")
	@Getter
	@Setter
	private Integer page = 1;

	@ApiModelProperty(value = "排序参数")
	@Getter
	@Setter
	private String sort;

	@ApiModelProperty(value = "模糊查询筛选参数")
	@Getter
	@Setter
	private String pattern;

	public Page toPageParam() {
		boolean asc = true;
		if (StringUtils.startsWith(sort, "-")) {
			sort = StringUtils.substring(sort, 1);
			asc = false;
		}

		Page page = new Page(this.page, this.perPage);

		// 设置排序字段
		OrderItem item = new OrderItem(sort, asc);
		List<OrderItem> orders = Lists.newArrayList();
		orders.add(item);
		page.setOrders(orders);

		return page;
	}

	public void setPer_page(Integer perPage) {
		this.perPage = perPage;
	}
}
