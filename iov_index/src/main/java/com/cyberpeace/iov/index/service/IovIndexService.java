package com.cyberpeace.iov.index.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.cyberpeace.iov.index.service.bo.index.ResourceCountBo;
import com.cyberpeace.iov.index.service.bo.index.RightDemo;
import com.cyberpeace.iov.index.service.bo.index.TaskBo;

public interface IovIndexService {

	RightDemo getDemo(HttpServletRequest request);

	ResourceCountBo getResourceCount();

	/**
	 * 获取tasklist
	 * 单兵 + 团队
	 *
	 * @return {@code List<TaskBo> }
	 */
	List<TaskBo> getTaskList();

}
