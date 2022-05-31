package com.cyberpeace.cs.dao.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cs.dao.entity.GxTask;
import com.cyberpeace.cs.service.context.GxTaskListContext;
import com.cyberpeace.cs.service.context.GxTaskListQuery;

@Mapper
public interface GxTaskMapper extends BaseMapper<GxTask> {

	IPage<GxTaskListContext> listTasks(Page page, GxTaskListQuery query);

	GxTask getByTaskId(String taskId);

	List<GxTask> selectByCloseTime(Date closeTime);

	List<String> listTaskId();

}
