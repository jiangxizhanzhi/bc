package com.cyberpeace.rtks.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.rtks.dao.entity.Task;

public interface TaskService extends IService<Task> {

	ListRsp<Task> page(PageReqBase pageReqBase);

	/**
	 * 给定数组的列表，只要其中任意一个id对应的职责不存在 报错
	 * */
	void checkIds(List<String> ids);

}
