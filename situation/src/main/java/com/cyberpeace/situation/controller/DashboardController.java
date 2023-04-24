package com.cyberpeace.situation.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.ListUtil;
import com.cyberpeace.common.util.SystemPropUtil;
import com.cyberpeace.integratation.constant.TaskStatus;
import com.cyberpeace.integratation.model.situation.*;
import com.cyberpeace.integratation.model.situation.node.NodeMigrationContext;
import com.cyberpeace.integratation.model.situation.req.SubjectListReq;
import com.cyberpeace.integratation.service.RemoteSituationService;
import com.cyberpeace.situation.constant.DashboardLevel;
import com.cyberpeace.situation.constant.DynamicParameter;
import com.cyberpeace.situation.constant.MapView;
import com.cyberpeace.situation.constant.SituationErrorCode;
import com.cyberpeace.situation.constant.ws.MessageType;
import com.cyberpeace.situation.controller.req.*;
import com.cyberpeace.situation.controller.res.DashboardBasicAttributeRes;
import com.cyberpeace.situation.controller.res.DashboardIdRes;
import com.cyberpeace.situation.dao.entity.SituationPublishInstance;
import com.cyberpeace.situation.dao.mapper.SituationPublishInstanceMapper;
import com.cyberpeace.situation.model.ws.WsEventAd;
import com.cyberpeace.situation.model.ws.WsMessage;
import com.cyberpeace.situation.service.*;
import com.cyberpeace.situation.service.context.JtTaskEventContext;
import com.cyberpeace.situation.service.context.NewPageContext;
import com.cyberpeace.situation.service.context.TopoParaContext;
import com.cyberpeace.situation.service.context.config.DashboardConfigContext;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "态势大屏")
@RestController
@RequiresAuthentication
@RequestMapping("/dashboard/")
public class DashboardController {

	@Autowired
	private DashboardService dashboardService;

	@Autowired
	private SituationHandlerService situationHandlerService;

	@Autowired
	private RemoteSituationService remoteSituationService;

	@Autowired
	private TopoHandlerService topoHandlerService;

	@Autowired
	private SituationPublishInstanceService situationPublishInstanceService;

	@Autowired
	private SituationPublishInstanceMapper situationPublishInstanceMapper;

	@Autowired
	private SituationTopoService situationTopoService;

	@ApiOperation("大屏跳转-获取大屏跳转信息（前台）")
	@GetMapping("/id/")
	public BaseRsp<DashboardIdRes> getDashboardId(
			@ApiParam(name = "task_id", value = "任务ID",
					required = true) @RequestParam(value = "task_id") String taskId,
			@ApiParam(name = "subject_id", value = "科目ID", required = false) @RequestParam(
					value = "subject_id", required = false) String subjectId,
			@ApiParam(name = "sub_range_id", value = "分靶场ID", required = false) @RequestParam(
					value = "sub_range_id", required = false) String subRangeId) {

		SubjectListReq subjectListReq = new SubjectListReq(subRangeId, taskId, subjectId);
		List<SubjectContext> subjectContexts = remoteSituationService
				.getSubjectList(subjectListReq);
		if (subjectContexts.size() != 1) {
			throw new BusinessException(SituationErrorCode.SUBJECT_INFO_ERROR);
		}

		SubjectContext subject = subjectContexts.get(0);

		// 校验状态
		if (!TaskStatus.RWZX.getName().equals(subject.getTaskStatus())) {
			throw new BusinessException(SituationErrorCode.TASK_NOT_RUNNING);
		}

		DashboardIdRes dashboardId = dashboardService.getAttributeByTaskId(taskId, subjectId);
		return new BaseRsp<>(dashboardId);

	}

	@ApiOperation("大屏跳转-获取大屏跳转信息（后台）")
	@GetMapping("/attribute/type/")
	public BaseRsp<DashboardIdRes>
			getDashboardTypeAttribute(@RequestParam("dashboard_id") @ApiParam(required = true,
					name = "dashboard_id", value = "大屏ID") String dashboardId) {
		return new BaseRsp<>(dashboardService.getAttributeByDashboardId(dashboardId));
	}

	@ApiOperation("态势运行-获取大屏基本属性")
	@GetMapping("/basic_attribute/")
	public BaseRsp<DashboardBasicAttributeRes>
			getDashboardBasicAttribute(@RequestParam("dashboard_id") @ApiParam(required = true,
					name = "dashboard_id", value = "大屏ID") String dashboardId) {
		if (dashboardId.equals("demo")) {
			return new BaseRsp<>(new DashboardBasicAttributeRes(dashboardId, 1, "态势大屏", "大屏预览",
					null, null, new Date(), MapView.ZH.getViewAngle()));
		}
		return new BaseRsp<>(dashboardService.getDashboardBasicAttribute(dashboardId));
	}

	@ApiOperation("态势运行-获取大屏配置属性")
	@GetMapping("/config/parameter/")
	public BaseRsp<DashboardConfigContext>
			getDashboardParameterConfig(@RequestParam("dashboard_id") @ApiParam(required = true,
					name = "dashboard_id", value = "大屏ID") String dashboardId) {
		return new BaseRsp<>(dashboardService.getParameterConfigByDashboardId(dashboardId));
	}

	@ApiOperation("态势选项-获取大屏动态属性选项")
	@GetMapping("/dynamic_parameter/")
	public BaseRsp<List<JSONObject>> getDynamicParameter() {
		return new BaseRsp<>(DynamicParameter.getTaskDynamicParameter());
	}

	@ApiOperation("态势选项-获取大屏刷新时间选项")
	@GetMapping("/refresh/list/")
	public BaseRsp<List<Integer>> getRefreshList() {
		return new BaseRsp<>(ListUtil.listRangeInt(5, 10));
	}

	@ApiOperation("态势消息-推送通用消息")
	@PostMapping("/change_notice/")
	public BaseRsp<Boolean>
			pushDashboardChange(@RequestBody @Validated DashboardChangeReq dashboardChangeReq) {
		WsMessage<JSONObject> wsMessage = new WsMessage<>(dashboardChangeReq.getCode(),
				dashboardChangeReq.getData());
		if (dashboardChangeReq.getFeatureId() == null) {
			situationHandlerService.pushDataToDashboard(wsMessage,
					dashboardChangeReq.getDashboardId());
		} else {
			situationHandlerService.pushDataToDashboard(wsMessage,
					dashboardChangeReq.getDashboardId(), dashboardChangeReq.getFeatureId());
		}
		return new BaseRsp<>(true);
	}

	@ApiOperation("态势消息-推送打开首页消息")
	@PostMapping("/back_home/")
	public BaseRsp<Boolean>
			pushBackHomeNotice(@RequestBody @Validated DashboardBasicReq dashboardBasicReq) {
		// 推送返回靶场大屏消息
		NewPageContext message = new NewPageContext(SystemPropUtil.getProp("BC_CODE"));
		WsMessage<NewPageContext> wsMessage = new WsMessage<>(MessageType.JumpPage.getDesc(),
				message);
		situationHandlerService.pushDataToDashboard(wsMessage, dashboardBasicReq.getDashboardId(),
				dashboardBasicReq.getFeatureId());
		return new BaseRsp<>(true);
	}

	@ApiOperation("态势动效-获取大屏通用刷新数据")
	@PostMapping("/event/refresh/")
	public BaseRsp<Boolean> refreshEvent(@RequestBody @Validated EventRefreshReq req) {

		// 定制大屏预览时，直接返回TRUE
		if (req.getDashboardId().equals("demo")) {
			return new BaseRsp<>(true);
		}

		Integer level = dashboardService.getDashboardLevel(req.getDashboardId());
		if (DashboardLevel.RW.getCode().equals(level)) {
			SituationPublishInstance instance = situationPublishInstanceMapper
					.selectById(req.getDashboardId());
			getAdEvents(getEventMsgReq(req, instance));
			getTaskNodeEvents(getEventMsgReq(req, instance));
			getNodeMigrationEvents(getEventMsgReq(req, instance));
			getTopoChangeEvents(getEventMsgReq(req, instance));
		} else {
			TaskEventMsgReq taskEventMsgReq = BeanUtils.copy(req, TaskEventMsgReq.class);
			getTaskEvents(taskEventMsgReq);
		}
		return new BaseRsp<>(true);
	}

	private EventMsgReq getEventMsgReq(EventRefreshReq req, SituationPublishInstance instance) {
		EventMsgReq eventMsgReq = BeanUtils.copy(req, EventMsgReq.class);
		eventMsgReq.writeInfoFromInstance(instance);
		return eventMsgReq;
	}

	private Boolean getTaskEvents(TaskEventMsgReq taskEventReq) {
		List<TaskEventContext> taskEventContexts = remoteSituationService
				.getTaskEventList(taskEventReq, taskEventReq.getFeatureId());
		situationHandlerService.batchPushDataToDashboard(MessageType.TaskEvent.getDesc(),
				taskEventReq.getDashboardId(), taskEventReq.getFeatureId(), taskEventContexts, null,
				null);
		return true;
	}

	private Boolean getAdEvents(EventMsgReq eventReq) {

		if (eventReq.getDashboardId().equals("demo")) {
			return true;
		}

		// 获取三方攻防事件数据
		List<AdEventContext> adEventContexts = remoteSituationService.getAdEventList(eventReq,
				eventReq.getFeatureId());

		// 获取拓扑配置
		String envId = situationPublishInstanceService
				.getEnvIdByDashboardId(eventReq.getDashboardId());
		Map<String, ElementSlimContext> elementSlimContextMap = topoHandlerService
				.getTopoConfigByEnvId(envId);

		// 处理事件数据
		List<WsEventAd> eventAds = situationHandlerService.eventContext2EventAd(adEventContexts,
				elementSlimContextMap);

		// 推送攻防事件数据
		Boolean result = situationHandlerService.pushEventAds(eventAds, eventReq.getDashboardId(),
				eventReq.getFeatureId());

		return result;
	}

	private Boolean getTaskNodeEvents(EventMsgReq eventReq) {

		List<NodeEventContext> nodeEventContexts = remoteSituationService.getNodeEventList(eventReq,
				eventReq.getFeatureId());

		nodeEventContexts.forEach(nodeEventContext -> {

			// 推送节点状态消息
			WsMessage<NodeEventContext> wsMessage = new WsMessage<>(
					MessageType.TaskEventNode.getDesc(), nodeEventContext);
			situationHandlerService.pushDataToDashboard(wsMessage, eventReq.getDashboardId(),
					eventReq.getFeatureId());

			// 推送链路状态消息
			nodeEventContext.getConnectionStatus().forEach(linkEventContext -> {
				WsMessage<LinkEventContext> linkMessage = new WsMessage<>(
						MessageType.LinkStatus.getDesc(), linkEventContext);
				situationHandlerService.pushDataToDashboard(linkMessage, eventReq.getDashboardId(),
						eventReq.getFeatureId());
			});
		});

		return true;

	}

	private Boolean getNodeMigrationEvents(EventMsgReq eventReq) {
		List<NodeMigrationContext> nodeEventContexts = remoteSituationService
				.getNodeMigrations(eventReq, eventReq.getFeatureId());
		situationHandlerService.batchPushDataToDashboard(MessageType.NodeMigration.getDesc(),
				eventReq.getDashboardId(), eventReq.getFeatureId(), nodeEventContexts, null, null);
		return true;
	}

	private Boolean getTopoChangeEvents(EventMsgReq eventReq) {
		TopoParaContext topoParaContext = situationTopoService.getCurrentTopo(eventReq);
		if (topoParaContext != null) {
			WsMessage<TopoParaContext> wsMessage = new WsMessage<>(MessageType.ChangeTopo.getDesc(),
					topoParaContext);
			situationHandlerService.pushDataToDashboard(wsMessage, eventReq.getDashboardId(),
					eventReq.getFeatureId());
		}
		return true;
	}

	@ApiOperation("态势动效-获取任务解题事件")
	@GetMapping("/event/task/jt/")
	public BaseRsp<Boolean> getJtTaskEvent(EventQueryReq eventQueryReq) {

		JtTaskEventContext jtTaskEventContext = new JtTaskEventContext();
		WsMessage<JtTaskEventContext> wsMessage = new WsMessage<>(MessageType.TaskEventJt.getDesc(),
				jtTaskEventContext);
		String sceneId = eventQueryReq.getDashboardId() + "_" + eventQueryReq.getFeatureId();
		situationHandlerService.pushDataToRoom(wsMessage, sceneId);

		return new BaseRsp<>(true);
	}

}
