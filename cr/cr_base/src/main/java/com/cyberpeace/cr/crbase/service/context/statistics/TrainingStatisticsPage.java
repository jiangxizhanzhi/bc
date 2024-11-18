package com.cyberpeace.cr.crbase.service.context.statistics;

import java.io.Serializable;

import com.cyberpeace.common.model.PageReqBase;

import lombok.Data;

/**
 * 训练统计分页
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class TrainingStatisticsPage implements Serializable {

	/**
	 * 分页
	 *
	 * @see Integer
	 */
	private Integer page;

	/**
	 * 每分页
	 *
	 * @see Integer
	 */
	private Integer perPage;

	/**
	 * 排序
	 *
	 * @see Boolean
	 */
	private Boolean sort;

	public TrainingStatisticsPage(PageReqBase req) {
		this.page = req.getPage();
		this.perPage = req.getPerPage();
		this.sort = "asc".equals(req.getSort());
	}
}
