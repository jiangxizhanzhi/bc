package com.cyberpeace.oj.rtks.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.oj.common.model.ListRsp;
import com.cyberpeace.oj.common.util.PageUtil;
import com.cyberpeace.oj.rtks.controller.req.TaskListReq;
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
    public ListRsp<Task> page(TaskListReq taskListReq) {
        Page page = taskListReq.toPageParam();
        IPage<Task> result = taskMapper.selectPage(page,
                new QueryWrapper<Task>().
                        like("content", taskListReq.getPattern()).or().like("resource_id", taskListReq.getPattern())
        );
        return PageUtil.getListResult(result);
    }
}
