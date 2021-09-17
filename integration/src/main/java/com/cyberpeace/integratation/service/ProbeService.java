package com.cyberpeace.integratation.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.evaluation.ProbeOriginPageData;

import java.util.Date;

/**
 * 集成靶场探针服务
 */
public interface ProbeService {

	// 获取场景id、探针id、主机id、起始时间、结束时间内的分页数据;
	ProbeOriginPageData originData(String sceneId, String hostId, String collectorId,
								   Date startTime, Date endTime, Page pageBase);
}
