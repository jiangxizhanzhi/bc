package com.cyberpeace.common.model;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页返回数据模型
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListRsp<T> implements Serializable {

	/**
	 * 列表
	 */
	@ApiModelProperty(value = "列表")
	private List<T> list = Lists.newArrayList();

	/**
	 * 总
	 */
	@ApiModelProperty(value = "总页数")
	private long total;

	/**
	 * 最后分页
	 */
	@ApiModelProperty(value = "最后页")
	private long lastPage;

	/**
	 * 当前分页
	 */
	@ApiModelProperty(value = "当前页")
	private long currentPage;
}
