package com.cyberpeace.common.model;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 分页请求体基础
 *
 * @author SN
 * @date 2024/11/08
 */
@ApiModel
public class PageReqBase extends PatternBase {

	/**
	 * 每分页
	 */
	@ApiModelProperty(name = "per_page", value = "每页大小")
	@Getter
	@Setter
	private Integer perPage = 10;

	/**
	 * 分页
	 */
	@ApiModelProperty(value = "页号，从1开始")
	@Getter
	@Setter
	private Integer page = 1;

	/**
	 * 排序
	 */
	@ApiModelProperty(value = "排序参数")
	@Getter
	@Setter
	private String sort;

	public Page toPageParam() {
		Page page = new Page(this.page, this.perPage);

		if (StringUtils.isBlank(sort)) {
			return page;
		}
		boolean asc = true;
		if (StringUtils.startsWith(sort, "-")) {
			sort = StringUtils.substring(sort, 1);
			asc = false;
		}

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

	public void setPage(Integer page) {
		this.page = page;
	}
}
