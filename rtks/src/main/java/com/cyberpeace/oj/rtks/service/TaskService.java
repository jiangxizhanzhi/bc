package com.cyberpeace.oj.rtks.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.oj.common.model.ListRsp;
import com.cyberpeace.oj.rtks.controller.req.TaskListReq;
import com.cyberpeace.oj.rtks.dao.entity.Task;

public interface TaskService extends IService<Task> {

    ListRsp<Task> page(TaskListReq taskListReq);
}
