package com.cyberpeace.cs.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.cs.dao.entity.GxLocalOrg;
import com.cyberpeace.cs.dao.entity.GxTask;
import com.cyberpeace.cs.service.context.GxTaskContext;
import com.cyberpeace.cs.service.context.GxTaskListContext;
import com.cyberpeace.cs.service.context.GxTaskListQuery;
import com.cyberpeace.integratation.model.gx.TrainResult;

public interface GxTaskService extends IService<GxTask> {

	Boolean create(GxTaskContext gxTaskContext);

	IPage<GxTaskListContext> list(GxTaskListQuery query);

	/**创建本地组织*/
	String createTaskOrg(List<String> userList, String classRoomId, String orgName);

	/**获取本地组织下用户列表*/
	List<String> getLocalUserList(GxLocalOrg gxLocalOrg);

	/**获取本地组织下用户信息列表*/
	List<UserInfo> getUserInfosByOrgId(String orgId);

	/**清除课程表相关的组织*/
	Boolean clearClassRoomOrg(String classRoomId);

	/**从接口消息中提取任务数据*/
	GxTaskContext getContextByData(JSONObject userTask, String token);

	/**从队列消息中提取任务数据*/
	GxTaskContext getContextByMsg(JSONObject message);

	/**上报个人课程任务结束*/
	Boolean finishClassroom(String lessonId);

	TrainResult getTrainResult(GxTask gxTask, String userId);

	/**上报整体课程任务结束*/
	Boolean finishClassroomTask(GxTask gxTask, String userId, String token);

	/**获取远程新任务*/
	Boolean getGxTasks(String token);

}
