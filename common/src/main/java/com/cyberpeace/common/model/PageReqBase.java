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

/**
 * 页面要求基础
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@ApiModel
public class PageReqBase implements Serializable {

	@ApiModelProperty(name = "per_page", value = "每页大小")
	@Getter
	@Setter
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

	/**
	 * 页面参数
	 *
	 * @return {@link Page}
	 */
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

	/**
	 * 设置每页大小
	 *
	 * @param perPage 每个页面
	 */
	public void setPer_page(Integer perPage) {
		this.perPage = perPage;
	}

	/**
	 * 设置页面
	 *
	 * @param page 页面
	 */
	public void setPage(Integer page) {
		this.page = page;
	}
}
