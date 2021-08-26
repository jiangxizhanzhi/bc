package com.cyberpeace.oj.rtks.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.oj.rtks.dao.entity.Task;
import com.cyberpeace.oj.rtks.dao.mapper.TaskMapper;
import com.cyberpeace.oj.rtks.service.TaskService;

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
	public List<String> filterIds(List<String> ids) {
		return taskMapper.filterIds(ids);
	}
}
