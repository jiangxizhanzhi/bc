package com.cyberpeace.cs.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cs.dao.entity.GxOrgUsers;
import com.cyberpeace.cs.dao.entity.GxTask;
import com.cyberpeace.cs.service.context.GxTaskContext;
import com.cyberpeace.cs.service.context.GxTaskListContext;
import com.cyberpeace.cs.service.context.GxTaskListQuery;

import java.util.List;

public interface GxTaskService extends IService<GxTask> {

	Boolean create(GxTaskContext gxTaskContext);

	IPage<GxTaskListContext> list(GxTaskListQuery query);

	String createTaskOrg(List<String> userList, String classRoomId, String orgName);

	List<String> getLocalUserList(GxOrgUsers gxOrgUsers);

	Boolean clearClassRoomOrg(String classRoomId);

}
