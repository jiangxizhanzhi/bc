package com.cyberpeace.oj.rtks.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.oj.common.exception.ResourceNotFoundException;
import com.cyberpeace.oj.common.model.ListRsp;
import com.cyberpeace.oj.common.model.PageReqBase;
import com.cyberpeace.oj.common.util.PageUtil;
import com.cyberpeace.oj.rtks.dao.entity.Task;
import com.cyberpeace.oj.rtks.dao.mapper.TaskMapper;
import com.cyberpeace.oj.rtks.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {

	@Autowired
	private TaskMapper taskMapper;

	@Override
	public ListRsp<Task> page(PageReqBase pageReqBase) {
		Page page = pageReqBase.toPageParam();
		IPage<Task> result = taskMapper.listTask(page, pageReqBase.getPattern());

		return PageUtil.getListResult(result);
	}

	@Override
	public void ensureTaskExists(String taskId) {
		Task task = getById(taskId);
		if (task == null) {
			throw new ResourceNotFoundException("岗位职责" + taskId + "未找到");
		}
	}
}
