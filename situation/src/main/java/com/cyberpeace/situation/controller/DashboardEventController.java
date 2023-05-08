package com.cyberpeace.situation.controller;

import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.integratation.constant.TaskEvent;
import com.cyberpeace.integratation.constant.TaskMode;
import com.cyberpeace.integratation.model.situation.*;
import com.cyberpeace.integratation.model.situation.req.TaskListReq;
import com.cyberpeace.integratation.service.RemoteSituationService;
import com.cyberpeace.situation.constant.gis.SiteType;
import com.cyberpeace.situation.constant.ws.MessageType;
import com.cyberpeace.situation.controller.req.EventMessageReq;
import com.cyberpeace.situation.controller.req.EventTaskReq;
import com.cyberpeace.situation.model.ws.*;
import com.cyberpeace.situation.service.SituationHandlerService;
import com.cyberpeace.situation.service.SituationPublishInstanceService;
import com.cyberpeace.situation.service.ThirdPartyService;
import com.cyberpeace.situation.service.TopoHandlerService;
import com.cyberpeace.situation.service.context.site.CenterContext;
import com.cyberpeace.situation.service.context.site.RangeContext;
import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 推送事件
 *
 * @author chenhy
 * @date 2023/05/08
 */
@Api(tags = "态势大屏-推送事件")
@RestController
@RequiresAuthentication
@RequestMapping("/situation/simulation")
public class DashboardEventController {

	@Autowired
	private SituationHandlerService situationHandlerService;

	@Autowired
	private TopoHandlerService topoHandlerService;

	@Autowired
	private SituationPublishInstanceService situationPublishInstanceService;

	@Autowired
	private RemoteSituationService remoteSituationService;

	@Autowired
	private ThirdPartyService thirdPartyService;

	/**
	 * 推送随机攻防动效
	 *
	 * @param dashboardId 大屏ID
	 * @param eventType   事件类型
	 * @param eventName   事件名称
	 * @return {@code BaseRsp<List<WsEventAd>>}
	 */
	@ApiOperation("推送随机攻防动效")
	@GetMapping("event_ad")
	public BaseRsp<List<WsEventAd>> randomEventAd(
			@ApiParam(name = "dashboard_id", value = "大屏ID") @RequestParam(value = "dashboard_id",
					required = true) String dashboardId,
			@ApiParam(name = "event_type", value = "事件类型") @RequestParam(value = "event_type",
					required = false) String eventType,
			@ApiParam(name = "event_name", value = "事件名称") @RequestParam(value = "event_name",
					required = false) String eventName) {

		// 获取随机两个节点ID
		String envId = situationPublishInstanceService.getEnvIdByDashboardId(dashboardId);
		Map<String, ElementSlimContext> elementSlimContextMap = topoHandlerService
				.getTopoConfigByEnvId(envId);
		Random random = new Random();
		String[] elementIds = elementSlimContextMap.keySet().toArray(new String[0]);
		String sourceId = elementIds[random.nextInt(elementIds.length)];
		String targetId = elementIds[random.nextInt(elementIds.length)];

		// 构造事件数据
		AdEventContext adEventContext = new AdEventContext(sourceId, targetId, eventName,
				eventType);

		// 处理事件数据
		List<WsEventAd> eventAds = situationHandlerService
				.eventContext2EventAd(Lists.newArrayList(adEventContext), elementSlimContextMap);

		// 推送攻防事件数据
		situationHandlerService.pushEventAds(eventAds, dashboardId, null);

		// 返回消息数据
		BaseRsp<List<WsEventAd>> rsp = new BaseRsp<>(eventAds);
		rsp.setMessage("成功推送事件");
		return rsp;
	}

	/**
	 * 推送随机节点工作状态
	 *
	 * @param dashboardId 大屏ID
	 * @param workStatus  工作状态 0-工作中 1-离线
	 * @return {@code BaseRsp<NodeEventContext>}
	 */
	@ApiOperation("推送随机节点工作状态")
	@GetMapping("event_node")
	public BaseRsp<NodeEventContext> randomEventNode(
			@ApiParam(name = "dashboard_id", value = "大屏ID") @RequestParam(value = "dashboard_id",
					required = true) String dashboardId,
			@ApiParam(name = "work_status", value = "工作状态 0-工作中 1-离线") @RequestParam(
					value = "work_status", required = true) Integer workStatus) {

		// 获取随机节点
		String envId = situationPublishInstanceService.getEnvIdByDashboardId(dashboardId);
		Map<String, ElementSlimContext> elementSlimContextMap = topoHandlerService
				.getTopoConfigByEnvId(envId);
		Random random = new Random();
		String[] elementIds = elementSlimContextMap.keySet().toArray(new String[0]);
		String nodeId = elementIds[random.nextInt(elementIds.length)];

		// 构造节点状态消息
		NodeEventContext nodeEventContext = new NodeEventContext(nodeId, workStatus);
		situationHandlerService.batchPushDataToDashboard(MessageType.TaskEventNode.getDesc(),
				dashboardId, null, Lists.newArrayList(nodeEventContext), null, null);
		return new BaseRsp<>(nodeEventContext);
	}

	/**
	 * 推送随机任务动效
	 *
	 * @param dashboardId 大屏ID
	 * @param taskEvent   任务操作 1-下发 2-开始 3-结束
	 * @return {@code BaseRsp<TaskEventContext>}
	 */
	@ApiOperation("推送随机任务动效")
	@GetMapping("event_task")
	public BaseRsp<TaskEventContext> randomEventTask(
			@ApiParam(name = "dashboard_id", value = "大屏ID") @RequestParam(value = "dashboard_id",
					required = true) String dashboardId,
			@ApiParam(name = "task_event", value = "任务操作 1-下发 2-开始 3-结束") @RequestParam(
					value = "task_event", required = false) String taskEvent) {

		// 获取随机任务
		List<TaskContext> tasks = remoteSituationService.getTaskList(new TaskListReq()).getList();
		Random random = new Random();
		TaskContext task = tasks.get(random.nextInt(tasks.size()));

		// 获取靶场数据
		String rangeName = StringUtils.isBlank(task.getResponsibleRangeName()) ? "综合性分靶场"
				: task.getResponsibleRangeName();
		RangeContext range = thirdPartyService.getRangeByName(rangeName);
		CenterContext center = thirdPartyService.getCenters().get(0);

		// 构造任务事件数据
		TaskEventContext taskEventContext = BeanUtils.deepCopy(task, TaskEventContext.class);
		taskEventContext.setResponsibleRangeId(range.getSubRangeId());

		// 处理任务事件类型
		if (taskEvent == null) {
			taskEvent = TaskEvent.getRandomValue(TaskEvent.values());
		} else {
			String finalTaskEvent = taskEvent;
			taskEvent = Arrays.stream(TaskEvent.class.getEnumConstants())
					.filter(t -> t.getCode().equals(finalTaskEvent)).findFirst()
					.orElse(TaskEvent.RWXF).getName();
		}
		taskEventContext.setTaskEvent(taskEvent);

		String eventDesc = null;

		if (TaskEvent.RWXF.getName().equals(taskEvent)) {
			// 构造源节点数据
			taskEventContext.setSourceType(SiteType.CENTER.getCode());
			taskEventContext.setSourceId("01");

			// 构造目标节点数据
			TargetSiteContext mainRange = new TargetSiteContext(SiteType.RANGE.getCode(),
					range.getSubRangeId());
			List<TargetSiteContext> targetSiteContexts = Lists.newArrayList(mainRange);
			String bcList = range.getSubRangeName();

			if (!TaskMode.DL.getName().equals(task.getTaskMode())) {
				List<RangeContext> rangeContexts = thirdPartyService.getRanges(null);
				RangeContext tmpRange = rangeContexts.get(random.nextInt(rangeContexts.size()));
				if (!tmpRange.getSubRangeId().equals(range.getSubRangeId())) {
					targetSiteContexts.add(new TargetSiteContext(SiteType.RANGE.getCode(),
							tmpRange.getSubRangeId()));
					bcList = bcList + "、" + tmpRange.getSubRangeName();
				}
			}
			taskEventContext.setTargetSites(targetSiteContexts);
			eventDesc = String.format("%s 向 %s 下发 %s", center.getCenterName(), bcList,
					task.getTaskName());

		} else if (TaskEvent.RWKS.getName().equals(taskEvent)) {
			eventDesc = String.format("%s 开始执行 %s", range.getSubRangeName(), task.getTaskName());
		} else if (TaskEvent.RWJS.getName().equals(taskEvent)) {
			eventDesc = String.format("%s 结束执行 %s", range.getSubRangeName(), task.getTaskName());
		} else {
			return new BaseRsp<>();
		}

		taskEventContext.setEventDesc(eventDesc);
		taskEventContext.setEventTime(new Date());
		WsMessage<TaskEventContext> message = new WsMessage<>(MessageType.TaskEvent.getDesc(),
				taskEventContext);
		situationHandlerService.pushDataToDashboard(message, dashboardId);
		return new BaseRsp<>(taskEventContext);
	}

	/**
	 * 推送自定义任务动效
	 *
	 * @param eventTaskReq 任务事件请求
	 * @return {@code BaseRsp<TaskEventContext>}
	 */
	@ApiOperation("推送自定义任务动效")
	@PostMapping("event_task")
	public BaseRsp<TaskEventContext> pushEventTask(@RequestBody EventTaskReq eventTaskReq) {
		situationHandlerService.batchPushDataToDashboard(MessageType.TaskEvent.getDesc(),
				eventTaskReq.getDashboardId(), eventTaskReq.getFeatureId(),
				Lists.newArrayList(eventTaskReq.getTaskEvent()), null, null);
		return new BaseRsp<>(eventTaskReq.getTaskEvent());
	}

	/**
	 * 推送多节点动效
	 *
	 * @param eventMessageReq 事件消息请求
	 * @return {@code BaseRsp<WsMessage<WsMultiNodeDynamic>>}
	 */
	@ApiOperation("推送多节点动效")
	@PostMapping("multi_event")
	public BaseRsp<WsMessage<WsMultiNodeDynamic>>
			multiEvent(@RequestBody EventMessageReq eventMessageReq) {
		String code = eventMessageReq.getCode();
		String sceneId = eventMessageReq.getSceneId();
		WsMessage<WsMultiNodeDynamic> wsMessage = new WsMessage<>(code, eventMessageReq.getData());
		situationHandlerService.pushDataToRoom(wsMessage, sceneId);
		return new BaseRsp<>(wsMessage);
	}

	/**
	 * 推送单节点动效
	 *
	 * @param eventMessageReq 事件消息请求
	 * @return {@code BaseRsp<WsMessage<WsSingleNodeDynamic>>}
	 */
	@ApiOperation("推送单节点动效")
	@PostMapping("single_event")
	public BaseRsp<WsMessage<WsSingleNodeDynamic>>
			singleEvent(@RequestBody WsSingleNodeDynamic eventMessageReq) {
		String code = MessageType.SingleNodeDynamic.getDesc();
		String sceneId = eventMessageReq.getSceneId();
		WsMessage<WsSingleNodeDynamic> wsMessage = new WsMessage<>(code, eventMessageReq);
		situationHandlerService.pushDataToRoom(wsMessage, sceneId);
		return new BaseRsp<>(wsMessage);
	}

	/**
	 * 推送多媒体动效
	 *
	 * @param wsAttackEffectDynamic 多媒体动效
	 * @return {@code BaseRsp<WsMessage<WsAttackEffectDynamic>>}
	 */
	@ApiOperation("推送多媒体动效")
	@PostMapping("media_event")
	public BaseRsp<WsMessage<WsAttackEffectDynamic>>
			mediaEvent(@RequestBody WsAttackEffectDynamic wsAttackEffectDynamic) {
		String code = MessageType.AttackEffect.getDesc();
		String sceneId = wsAttackEffectDynamic.getSceneId();
		WsMessage<WsAttackEffectDynamic> wsMessage = new WsMessage<>(code, wsAttackEffectDynamic);
		situationHandlerService.pushDataToRoom(wsMessage, sceneId);
		return new BaseRsp<>(wsMessage);
	}
}
