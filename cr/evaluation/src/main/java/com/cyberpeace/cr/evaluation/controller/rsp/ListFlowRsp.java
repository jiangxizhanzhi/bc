package com.cyberpeace.cr.evaluation.controller.rsp;

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
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListFlowRsp<T> implements Serializable {

	private List<T> list = Lists.newArrayList();

	@ApiModelProperty("节点下全量流量任务状态 0-无运行任务 1-有运行任务")
	private Integer status;

	private long total;

	private long lastPage;

	private long currentPage;
}
