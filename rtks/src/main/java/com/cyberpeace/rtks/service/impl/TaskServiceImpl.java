package com.cyberpeace.rtks.service.impl;

import java.util.List;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.rtks.constant.RtksBusinessErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.rtks.dao.entity.Task;
import com.cyberpeace.rtks.dao.mapper.TaskMapper;
import com.cyberpeace.rtks.service.TaskService;
import org.springframework.stereotype.Service;

/**
 * 任务Service实现
 *
 * @author SN
 * @date 2024/11/07
 */
@Service
@Slf4j
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {

	/**
	 * 任务Mapper
	 */
	@Autowired
	private TaskMapper taskMapper;

	/**
	 * 分页
	 *
	 * @param pageReqBase 分页请求体基础
	 * @return {@code ListRsp<Task> }
	 */
	@Override
	public ListRsp<Task> page(PageReqBase pageReqBase) {
		Page page = pageReqBase.toPageParam();
		IPage<Task> result = taskMapper.listTask(page, pageReqBase.getPattern());

		return PageUtil.getListResult(result);
	}

	/**
	 * 检查id列表
	 *
	 * @param ids id列表
	 */
	@Override
	public void checkIds(List<String> ids) {
		List<String> existingIds = taskMapper.filterIds(ids);
		for (String id : ids) {
			if (!existingIds.contains(id)) {
				log.error("task not exist！taskId=[{}]", id);
				throw new BusinessException(RtksBusinessErrorCode.TASK_NOT_EXIST);
			}
		}
	}
}
