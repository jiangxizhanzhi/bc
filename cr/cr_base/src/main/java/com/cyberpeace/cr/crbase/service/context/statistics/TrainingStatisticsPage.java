package com.cyberpeace.cr.crbase.service.context.statistics;

import java.io.Serializable;

import com.cyberpeace.common.model.PageReqBase;

import lombok.Getter;
import lombok.Setter;

/**
 * 训练统计分页
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class TrainingStatisticsPage implements Serializable {

	/**
	 * 分页
	 */
	private Integer page;

	/**
	 * 每分页
	 */
	private Integer perPage;

	/**
	 * 排序
	 */
	private Boolean sort;

	public TrainingStatisticsPage(PageReqBase req) {
		this.page = req.getPage();
		this.perPage = req.getPerPage();
		this.sort = "asc".equals(req.getSort());
	}
}
