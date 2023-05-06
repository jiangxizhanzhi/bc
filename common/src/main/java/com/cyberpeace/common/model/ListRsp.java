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
 * 列出负责
 * 分页返回数据模型
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListRsp<T> implements Serializable {

	@ApiModelProperty(value = "列表")
	private List<T> list = Lists.newArrayList();

	@ApiModelProperty(value = "总页数")
	private long total;

	@ApiModelProperty(value = "最后页")
	private long lastPage;

	@ApiModelProperty(value = "当前页")
	private long currentPage;
}
