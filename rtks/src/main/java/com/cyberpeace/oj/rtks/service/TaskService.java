package com.cyberpeace.oj.rtks.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.oj.rtks.dao.entity.Task;

public interface TaskService extends IService<Task> {

	ListRsp<Task> page(PageReqBase pageReqBase);

	List<String> filterIds(List<String> ids);

}
