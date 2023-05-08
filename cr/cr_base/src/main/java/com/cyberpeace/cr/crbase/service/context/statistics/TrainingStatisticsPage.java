package com.cyberpeace.cr.crbase.service.context.statistics;

import java.io.Serializable;

import com.cyberpeace.common.model.PageReqBase;

import lombok.Data;

/**
 * 培训统计页面
 * 统计分页
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class TrainingStatisticsPage implements Serializable {

	private Integer page;

	private Integer perPage;

	private Boolean sort;

	public TrainingStatisticsPage(PageReqBase req) {
		this.page = req.getPage();
		this.perPage = req.getPerPage();
		this.sort = "asc".equals(req.getSort());
	}
}
