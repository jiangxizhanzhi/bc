package com.cyberpeace.cs.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cs.dao.entity.GxTask;
import com.cyberpeace.cs.service.context.GxTaskContext;
import com.cyberpeace.cs.service.context.GxTaskListContext;
import com.cyberpeace.cs.service.context.GxTaskListQuery;

public interface GxTaskService extends IService<GxTask> {

	Boolean create(GxTaskContext gxTaskContext);

	IPage<GxTaskListContext> list(GxTaskListQuery query);

}
