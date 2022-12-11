package com.cyberpeace.integratation.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.HttpClientUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.integratation.model.situation.*;
import com.cyberpeace.integratation.model.situation.req.*;
import com.cyberpeace.integratation.model.situation.res.LgNodeActionPageRsp;
import com.cyberpeace.integratation.model.situation.res.LgTaskContextPageRes;
import com.cyberpeace.integratation.model.situation.res.LgUserActionRsp;
import com.cyberpeace.integratation.service.RemoteSituationService;
import com.google.common.collect.Lists;

@Service
public class RemoteSituationServiceImpl implements RemoteSituationService {

	private String TASK_URL;

	private String PLAN_URL;

	private String SUBJECT_URL;

	private String RANGE_INFO_URL;

	private String CENTER_INFO_URL;

	private String PLATFORM_LIST_URL;

	private String TASK_LIST_URL;

	private String NETWORK_INFO_URL;

	private String NODE_INFO_URL;

	private String NODE_ACTION_URL;

	private String NODE_TREND_URL;

	private String USER_ACTION_URL;

	private String USER_TREND_URL;

	private String TASK_EVENT_URL;

	private String AD_EVENT_URL;

	private String NODE_STATUS_URL;

	@PostConstruct
	public void init() {
		String outerHost = "http://" + ConfigUtil.getContextProperty("outer.host");
		TASK_URL = outerHost + ConfigUtil.getContextProperty("task.list");
		PLAN_URL = outerHost + ConfigUtil.getContextProperty("plan.list");
		SUBJECT_URL = outerHost + ConfigUtil.getContextProperty("subject.list");
		RANGE_INFO_URL = outerHost + ConfigUtil.getContextProperty("range.info");
		CENTER_INFO_URL = outerHost + ConfigUtil.getContextProperty("center.info");
		PLATFORM_LIST_URL = outerHost + ConfigUtil.getContextProperty("platform.list");
		TASK_LIST_URL = outerHost + ConfigUtil.getContextProperty("task.list.all");
		NETWORK_INFO_URL = outerHost + ConfigUtil.getContextProperty("network.info");
		NODE_INFO_URL = outerHost + ConfigUtil.getContextProperty("node.info");
		NODE_ACTION_URL = outerHost + ConfigUtil.getContextProperty("node.action");
		NODE_TREND_URL = outerHost + ConfigUtil.getContextProperty("node.trend");
		USER_ACTION_URL = outerHost + ConfigUtil.getContextProperty("user.action");
		USER_TREND_URL = outerHost + ConfigUtil.getContextProperty("user.trend");
		TASK_EVENT_URL = outerHost + ConfigUtil.getContextProperty("event.task");
		AD_EVENT_URL = outerHost + ConfigUtil.getContextProperty("event.ad");
		NODE_STATUS_URL = outerHost + ConfigUtil.getContextProperty("event.node");
	}

	@Override
	public List<SituationSubject> getSubjectByTasks(List<String> taskIds) {
		JSONArray tasks = HttpClientUtil.rawGet(SUBJECT_URL, JSONArray.class);
		List<SituationSubject> list = JSONArray.parseArray(tasks.toJSONString(),
				SituationSubject.class);
		return list;
	}

	private List<SituationSubject> getSubjectById(String taskId) {
		JSONObject tasks = HttpClientUtil.rawGet(SUBJECT_URL + taskId, JSONObject.class);
		List<SituationSubject> list = JSONArray.parseArray(
				JSONObject.toJSONString(tasks.getJSONArray("list")), SituationSubject.class);
		return list;
	}

	@Override
	public List<SituationSubject> getSubjectByTask(String taskId) {
		JSONObject tasks = HttpClientUtil.rawGet(SUBJECT_URL + taskId, JSONObject.class);
		List<SituationSubject> list = JSONArray.parseArray(
				JSONObject.toJSONString(tasks.getJSONArray("list")), SituationSubject.class);
		return list;
	}

	@Override
	public List<SituationTask> getTasks(String type, String resourceId) {
		JSONObject tasks = HttpClientUtil.rawGet(TASK_URL + type, JSONObject.class);
		List<SituationTask> list = JSONArray.parseArray(
				JSONObject.toJSONString(tasks.getJSONArray("list")), SituationTask.class);
		return list;
	}

	@Override
	public List<SituationPlan> getPlans() {
		return new ArrayList<>();
	}

	@Override
	public SubRangeInfoContext getSubRangeInfo(String subRangeId) {
		SubRangeInfoContext subRangeInfoContext = HttpClientUtil.rawGet(RANGE_INFO_URL + subRangeId,
				SubRangeInfoContext.class);
		return subRangeInfoContext;
	}

	@Override
	public CenterInfoContext getCenterInfo(String centerId) {
		CenterInfoContext centerInfoContext = HttpClientUtil.rawGet(CENTER_INFO_URL + centerId,
				CenterInfoContext.class);
		return centerInfoContext;
	}

	@Override
	public List<PlatformContext> getPlatformList(String taskId, String subjectId,
			String subRangeId) {
		JSONArray result = HttpClientUtil.rawGet(PLATFORM_LIST_URL + "?task_id=" + taskId
				+ "&subject_id=" + subjectId + "&sub_range_id=" + subRangeId, JSONArray.class);
		return JSONArray.parseArray(JSONObject.toJSONString(result), PlatformContext.class);
	}

	@Override
	public NetworkInfoContext getNetworkInfo(NodeListApiReq nodeListApiReq) {

		NetworkInfoContext networkInfoContext = HttpClientUtil.post(NETWORK_INFO_URL,
				nodeListApiReq, NetworkInfoContext.class);

		return networkInfoContext;
	}

	@Override
	public ListRsp<TaskContext> getTaskList(TaskListReq taskListReq) {
		LgTaskContextPageRes rsp = HttpClientUtil.post(TASK_LIST_URL, taskListReq,
				LgTaskContextPageRes.class);
		ListRsp<TaskContext> res = rsp.convert();
		res.setList(rsp.getList());
		return res;
	}

	@Override
	public NodeInfoContext getNodeInfo(NodeIdApiReq nodeIdApiReq) {
		NodeInfoContext context = HttpClientUtil.post(NODE_INFO_URL, nodeIdApiReq,
				NodeInfoContext.class);
		return context;
	}

	@Override
	public ListRsp<NodeActionContext> getNodeAction(NodeIdPageReq nodeIdPageReq) {
		LgNodeActionPageRsp rsp = HttpClientUtil.post(NODE_ACTION_URL, nodeIdPageReq,
				LgNodeActionPageRsp.class);
		ListRsp<NodeActionContext> res = rsp.convert();
		res.setList(rsp.getList());

		return res;
	}

	@Override
	public List<NodeTrendContext> getNodeTrend(NodeIdApiReq nodeIdApiReq) {
		String rsp = HttpClientUtil.post(NODE_TREND_URL, nodeIdApiReq, String.class);
		List<NodeTrendContext> res = JsonUtil.toJsonObjectArray(rsp, NodeTrendContext.class);
		return res;
	}

	@Override
	public ListRsp<UserActionContext> getUserAction(NodeIdPageReq nodeIdPageReq) {
		LgUserActionRsp rsp = HttpClientUtil.post(USER_ACTION_URL, nodeIdPageReq,
				LgUserActionRsp.class);
		ListRsp<UserActionContext> res = rsp.convert();
		res.setList(rsp.getList());
		return res;
	}

	@Override
	public List<UserTrendContext> getUserTrend(NodeIdApiReq nodeIdApiReq) {
		return Lists.newArrayList();
	}

	@Override
	public List<TaskEventContext> getTaskEventList(TaskEventReq taskEventReq) {
		String rsp = HttpClientUtil.rawGetByParam(taskEventReq, TASK_EVENT_URL, String.class);
		List<TaskEventContext> res = JsonUtil.toJsonObjectArray(rsp, TaskEventContext.class);
		return res;
	}

	@Override
	public List<AdEventContext> getAdEventList(EventReq eventReq) {
		String rsp = HttpClientUtil.post(AD_EVENT_URL, eventReq, String.class);
		List<AdEventContext> res = JsonUtil.toJsonObjectArray(rsp, AdEventContext.class);
		return res;
	}

	@Override
	public List<NodeEventContext> getNodeEventList(EventReq eventReq) {
		String rsp = HttpClientUtil.post(NODE_STATUS_URL, eventReq, String.class);
		List<NodeEventContext> res = JsonUtil.toJsonObjectArray(rsp, NodeEventContext.class);
		return res;
	}

	@Override
	public TopoContext getTopo(TopoReq topoReq) {
		return new TopoContext();
	}
}
