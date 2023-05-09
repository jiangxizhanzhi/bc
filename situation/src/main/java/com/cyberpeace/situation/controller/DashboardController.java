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
import com.cyberpeace.situation.constant.SituationErrorCode;
import com.cyberpeace.situation.constant.dashboard.DashboardLevel;
import com.cyberpeace.situation.constant.dashboard.DynamicParameter;
import com.cyberpeace.situation.constant.gis.MapView;
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

/**
 * 态势大屏
 *
 * @author chenhy
 * @date 2023/05/08
 */
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

	/**
	 * 获取大屏跳转信息（前台）
	 *
	 * @param taskId     任务id
	 * @param subjectId  科目id
	 * @param subRangeId 分靶场id
	 * @return {@code BaseRsp<DashboardIdRes>}
	 */
	@ApiOperation("大屏跳转-获取大屏跳转信息（前台）")
	@GetMapping("/id/")
	public BaseRsp<DashboardIdRes> getDashboardId(
			@ApiParam(name = "task_id", value = "任务ID",
					required = true) @RequestParam(value = "task_id") String taskId,
			@ApiParam(name = "subject_id", value = "科目ID", required = false) @RequestParam(
					value = "subject_id", required = false) String subjectId,
			@ApiParam(name = "sub_range_id", value = "分靶场ID", required = false) @RequestParam(
					value = "sub_range_id", required = false) String subRangeId) {

		// 获取科目信息
		SubjectListReq subjectListReq = new SubjectListReq(subRangeId, taskId, subjectId);
		List<SubjectContext> subjectContexts = remoteSituationService
				.getSubjectList(subjectListReq);

		// 校验科目信息
		if (subjectContexts.size() != 1) {
			throw new BusinessException(SituationErrorCode.SUBJECT_INFO_ERROR);
		}

		SubjectContext subject = subjectContexts.get(0);

		// 校验任务状态
		if (!TaskStatus.RWZX.getName().equals(subject.getTaskStatus())) {
			throw new BusinessException(SituationErrorCode.TASK_NOT_RUNNING);
		}

		// 构造大屏跳转信息
		DashboardIdRes dashboardId = dashboardService.getAttributeByTaskId(taskId, subjectId);
		return new BaseRsp<>(dashboardId);

	}

	/**
	 * 获取大屏跳转信息（后台）
	 *
	 * @param dashboardId 大屏ID
	 * @return {@code BaseRsp<DashboardIdRes>}
	 */
	@ApiOperation("大屏跳转-获取大屏跳转信息（后台）")
	@GetMapping("/attribute/type/")
	public BaseRsp<DashboardIdRes>
			getDashboardTypeAttribute(@RequestParam("dashboard_id") @ApiParam(required = true,
					name = "dashboard_id", value = "大屏ID") String dashboardId) {
		return new BaseRsp<>(dashboardService.getAttributeByDashboardId(dashboardId));
	}

	/**
	 * 获取大屏基本属性
	 *
	 * @param dashboardId 大屏ID
	 * @return {@code BaseRsp<DashboardBasicAttributeRes>}
	 */
	@ApiOperation("态势运行-获取大屏基本属性")
	@GetMapping("/basic_attribute/")
	public BaseRsp<DashboardBasicAttributeRes>
			getDashboardBasicAttribute(@RequestParam("dashboard_id") @ApiParam(required = true,
					name = "dashboard_id", value = "大屏ID") String dashboardId) {

		// 如果是demo大屏，直接返回demo大屏的基本属性
		if (dashboardId.equals("demo")) {
			return new BaseRsp<>(new DashboardBasicAttributeRes(dashboardId, 1, "态势大屏", "大屏预览",
					null, null, new Date(), MapView.ZH.getViewAngle()));
		}

		// 获取真实大屏基本属性
		return new BaseRsp<>(dashboardService.getDashboardBasicAttribute(dashboardId));
	}

	/**
	 * 获取大屏配置属性
	 *
	 * @param dashboardId 大屏ID
	 * @return {@code BaseRsp<DashboardConfigContext>}
	 */
	@ApiOperation("态势运行-获取大屏配置属性")
	@GetMapping("/config/parameter/")
	public BaseRsp<DashboardConfigContext>
			getDashboardParameterConfig(@RequestParam("dashboard_id") @ApiParam(required = true,
					name = "dashboard_id", value = "大屏ID") String dashboardId) {
		return new BaseRsp<>(dashboardService.getParameterConfigByDashboardId(dashboardId));
	}

	/**
	 * 获取大屏动态属性选项
	 *
	 * @return {@code BaseRsp<List<JSONObject>>}
	 */
	@ApiOperation("态势选项-获取大屏动态属性选项")
	@GetMapping("/dynamic_parameter/")
	public BaseRsp<List<JSONObject>> getDynamicParameter() {
		return new BaseRsp<>(DynamicParameter.getTaskDynamicParameter());
	}

	/**
	 * 获取大屏刷新时间选项
	 *
	 * @return {@code BaseRsp<List<Integer>>}
	 */
	@ApiOperation("态势选项-获取大屏刷新时间选项")
	@GetMapping("/refresh/list/")
	public BaseRsp<List<Integer>> getRefreshList() {
		return new BaseRsp<>(ListUtil.listRangeInt(5, 10));
	}

	/**
	 * 推送通用消息
	 *
	 * @param dashboardChangeReq 通用消息请求
	 * @return {@code BaseRsp<Boolean>}
	 */
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

	/**
	 * 推送打开首页消息
	 *
	 * @param dashboardBasicReq 大屏基本信息
	 * @return {@code BaseRsp<Boolean>}
	 */
	@ApiOperation("态势消息-推送打开首页消息")
	@PostMapping("/back_home/")
	public BaseRsp<Boolean>
			pushBackHomeNotice(@RequestBody @Validated DashboardBasicReq dashboardBasicReq) {

		// 获取当前靶场首页代码
		String bcCode = SystemPropUtil.getProp("BC_CODE");
		String homeCode = "01".equals(bcCode) ? "00" : bcCode;

		// 推送返回靶场大屏消息
		NewPageContext message = new NewPageContext(homeCode);
		WsMessage<NewPageContext> wsMessage = new WsMessage<>(MessageType.JumpPage.getDesc(),
				message);
		situationHandlerService.pushDataToDashboard(wsMessage, dashboardBasicReq.getDashboardId(),
				dashboardBasicReq.getFeatureId());
		return new BaseRsp<>(true);
	}

	/**
	 * 获取大屏通用刷新数据
	 *
	 * @param req 事件刷新请求
	 * @return {@code BaseRsp<Boolean>}
	 */
	@ApiOperation("态势动效-获取大屏通用刷新数据")
	@PostMapping("/event/refresh/")
	public BaseRsp<Boolean> refreshEvent(@RequestBody @Validated EventRefreshReq req) {

		// 定制大屏预览时，不刷新事件
		if (req.getDashboardId().equals("demo")) {
			return new BaseRsp<>(true);
		}

		// 获取大屏级别并依据大屏类型获取事件
		Integer level = dashboardService.getDashboardLevel(req.getDashboardId());
		if (DashboardLevel.RW.getCode().equals(level)) {
			// 如果是任务大屏，获取攻防事件
			SituationPublishInstance instance = situationPublishInstanceMapper
					.selectById(req.getDashboardId());
			getAdEvents(getEventMsgReq(req, instance));
			getTaskNodeEvents(getEventMsgReq(req, instance));
			getNodeMigrationEvents(getEventMsgReq(req, instance));
			getTopoChangeEvents(getEventMsgReq(req, instance));
		} else {
			// 如果是综合大屏，获取任务事件
			TaskEventMsgReq taskEventMsgReq = BeanUtils.copy(req, TaskEventMsgReq.class);
			getTaskEvents(taskEventMsgReq);
		}
		return new BaseRsp<>(true);
	}

	/**
	 * 获取攻防事件请求
	 *
	 * @param req      事件刷新请求
	 * @param instance 大屏实例
	 * @return {@code EventMsgReq}
	 */
	private EventMsgReq getEventMsgReq(EventRefreshReq req, SituationPublishInstance instance) {
		EventMsgReq eventMsgReq = BeanUtils.copy(req, EventMsgReq.class);
		eventMsgReq.writeInfoFromInstance(instance);
		return eventMsgReq;
	}

	/**
	 * 获取任务事件
	 *
	 * @param taskEventReq 任务事件请求
	 * @return {@code Boolean}
	 */
	private Boolean getTaskEvents(TaskEventMsgReq taskEventReq) {
		List<TaskEventContext> taskEventContexts = remoteSituationService
				.getTaskEventList(taskEventReq, taskEventReq.getFeatureId());
		situationHandlerService.batchPushDataToDashboard(MessageType.TaskEvent.getDesc(),
				taskEventReq.getDashboardId(), taskEventReq.getFeatureId(), taskEventContexts, null,
				null);
		return true;
	}

	/**
	 * 获取攻防事件
	 *
	 * @param eventReq 攻防事件请求
	 * @return {@code Boolean}
	 */
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

	/**
	 * 获取节点事件
	 *
	 * @param eventReq 事件请求
	 * @return {@code Boolean}
	 */
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

	/**
	 * 获取节点迁移事件
	 *
	 * @param eventReq 事件请求
	 * @return {@code Boolean}
	 */
	private Boolean getNodeMigrationEvents(EventMsgReq eventReq) {
		List<NodeMigrationContext> nodeEventContexts = remoteSituationService
				.getNodeMigrations(eventReq, eventReq.getFeatureId());
		situationHandlerService.batchPushDataToDashboard(MessageType.NodeMigration.getDesc(),
				eventReq.getDashboardId(), eventReq.getFeatureId(), nodeEventContexts, null, null);
		return true;
	}

	/**
	 * 获取拓扑变化事件
	 *
	 * @param eventReq 事件请求
	 * @return {@code Boolean}
	 */
	private Boolean getTopoChangeEvents(EventMsgReq eventReq) {

		// 获取拓扑变化事件
		TopoParaContext topoParaContext = situationTopoService.getCurrentTopo(eventReq);

		// 如果拓扑变化事件不为空，推送拓扑变化事件
		if (topoParaContext != null) {
			WsMessage<TopoParaContext> wsMessage = new WsMessage<>(MessageType.ChangeTopo.getDesc(),
					topoParaContext);
			situationHandlerService.pushDataToDashboard(wsMessage, eventReq.getDashboardId(),
					eventReq.getFeatureId());
		}
		return true;
	}

	/**
	 * 获取任务解题事件
	 *
	 * @param eventQueryReq 事件查询请求
	 * @return {@code BaseRsp<Boolean>}
	 */
	@ApiOperation("态势动效-获取任务解题事件")
	@GetMapping("/event/task/jt/")
	public BaseRsp<Boolean> getJtTaskEvent(EventQueryReq eventQueryReq) {

		// 获取任务解题事件
		JtTaskEventContext jtTaskEventContext = new JtTaskEventContext();
		WsMessage<JtTaskEventContext> wsMessage = new WsMessage<>(MessageType.TaskEventJt.getDesc(),
				jtTaskEventContext);
		String sceneId = eventQueryReq.getDashboardId() + "_" + eventQueryReq.getFeatureId();

		// 推送任务解题事件
		situationHandlerService.pushDataToRoom(wsMessage, sceneId);

		return new BaseRsp<>(true);
	}

}
