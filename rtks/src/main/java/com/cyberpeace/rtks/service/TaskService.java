package com.cyberpeace.rtks.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.rtks.dao.entity.Task;

/**
 * 任务Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see IService
 * @since 1.0.0
 */
public interface TaskService extends IService<Task> {

	/**
	 * 分页
	 *
	 * @param pageReqBase 分页请求体基础
	 * @return {@code ListRsp<Task> }
	 * @throws
	 * @see PageReqBase
	 * @see ListRsp<Task>
	 */
	ListRsp<Task> page(PageReqBase pageReqBase);

	/**
	 * 给定数组的列表，只要其中任意一个id对应的职责不存在 报错
	 *
	 * @param ids id列表
	 * @throws
	 * @see List<String>
	 */
	void checkIds(List<String> ids);

}
