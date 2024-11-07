package com.cyberpeace.cr.evaluation.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.cyberpeace.common.component.MyWebSocketHandler;
import com.cyberpeace.common.constant.WebConstant;
import com.cyberpeace.common.model.scene.MqContext;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.model.ws.WebSocketMessageContext;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.service.CrSceneService;
import com.cyberpeace.cr.crbase.service.context.scene.FlowUpdate;
import com.cyberpeace.cr.crbase.service.impl.CrEventListenerHelper;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTemplate;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalTaskMapper;
import com.cyberpeace.cr.evaluation.service.EvalTaskService;
import com.cyberpeace.cr.evaluation.service.EvalTemplateService;
import com.cyberpeace.cr.evaluation.service.context.EvalTaskOpsContext;
import com.cyberpeace.cs.constant.Operator;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.context.FlowUpdateContext;
import com.cyberpeace.cs.service.context.WsMessageBO;
import com.cyberpeace.cs.service.context.scene.ElementFlow;
import com.cyberpeace.cs.service.impl.MessageListener;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

/**
 * 评估场景事件听者
 *
 * @author SN
 * @date 2024/11/07
 */
@Service
@Slf4j
public class EvalSceneEventListener extends MessageListener {

	/**
	 * Web 套接字处理程序
	 */
	@Autowired
	private MyWebSocketHandler webSocketHandler;

	/**
	 * 靶场事件听者助手
	 */
	@Autowired
	private CrEventListenerHelper crEventListenerHelper;

	/**
	 * 靶场场景Service
	 */
	@Autowired
	private CrSceneService crSceneService;

	/**
	 * 评估任务Service
	 */
	@Autowired
	private EvalTaskService evalTaskService;

	/**
	 * 评估模板Service
	 */
	@Autowired
	private EvalTemplateService evalTemplateService;

	/**
	 * 评估任务Mapper
	 */
	@Autowired
	private EvalTaskMapper evalTaskMapper;

	/**
	 * 流程代码
	 */
	private List<String> FLOW_CODES = Lists.newArrayList("node_start_flow", "node_stop_flow");

	/**
	 * 声明消息类型
	 *
	 * @return {@code Integer }
	 */
	@Override
	public Integer declareMessageType() {
		return LocalScene.BusinessCode.RANGE_EVALUATION.getCode();
	}

	/**
	 * 上场景删除
	 *
	 * @param data       数据
	 * @param businessId 业务id
	 * @param context    上下文
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean onSceneDelete(String data, String businessId, MqContext context) {

		// 判断删除为靶场执行时，跳过后续操作
		String operator = JSON.parseObject(context.getPassThrough(), JSONObject.class)
				.getString(WebConstant.PASS_THROUGH_OPERATOR);
		if (StringUtils.isNotEmpty(operator) && operator.equals(Operator.BC.getDesc())) {
			return true;
		}

		EvalTaskOpsContext evalTaskOpsContext = evalTaskService.getEvalTaskOpsContext(businessId,
				false);

		if (evalTaskOpsContext.getEvalTask() == null) {
			return true;
		}

		evalTaskService.clearTaskAndScene(evalTaskOpsContext);

		return true;
	}

	/**
	 * 上场景开始过程
	 *
	 * @param data       数据
	 * @param businessId 业务id
	 * @param context    上下文
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean onSceneStartProcess(String data, String businessId, MqContext context) {
		pushDataToWeb(data, businessId);
		return true;
	}

	/**
	 * 上场景元素
	 *
	 * @param data       数据
	 * @param businessId 业务id
	 * @param context    上下文
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean onSceneElement(String data, String businessId, MqContext context) {

		JSONObject message = JsonUtil.toJsonObject(data, JSONObject.class).getJSONObject("data")
				.getJSONObject("data");
		String callAction = message.getString("call_action");

		if (callAction != null && FLOW_CODES.contains(callAction)) {
			processFlowMessage(context, businessId);
		}

		pushDataToWeb(data, businessId);
		return true;
	}

	/**
	 * 流程流消息
	 *
	 * @param context    上下文
	 * @param businessId 业务id
	 */
	private void processFlowMessage(MqContext context, String businessId) {

		String extension = JSON.parseObject(context.getPassThrough(), JSONObject.class)
				.getString(WebConstant.PASS_THROUGH_EXTENSION);

		// 上下文中无额外信息时，终止处理
		if (extension == null) {
			return;
		}

		// 提取上下文信息
		FlowUpdateContext flowUpdateContext = JsonUtil.toJsonObject(extension,
				FlowUpdateContext.class);
		List<String> flowIds = flowUpdateContext.getFlowIds();
		String nodeId = flowUpdateContext.getNodeId();
		String mpSceneId = flowUpdateContext.getMpSceneId();

		// 场景或节点id为空时，终止处理
		if (mpSceneId == null || nodeId == null) {
			return;
		}

		// 查询涉及流量的信息
		UserInfoContext.setUserContext(new UserInfo());
		List<ElementFlow> elementFlowList = crSceneService.getNodeFlowList(mpSceneId, null, nodeId);
		List<ElementFlow> updateFlows;
		if (flowIds != null && !flowIds.isEmpty()) {
			updateFlows = elementFlowList.stream()
					.filter(elementFlow -> flowIds.contains(elementFlow.getId()))
					.collect(Collectors.toList());
		} else {
			updateFlows = elementFlowList;
		}

		// 构造推送消息
		FlowUpdate flowUpdate = new FlowUpdate(mpSceneId, nodeId, updateFlows);
		WsMessageBO<FlowUpdate> wsMessageBO = new WsMessageBO<>("update_flow", flowUpdate);
		pushDataToWeb(JsonUtil.toJsonString(wsMessageBO, PropertyNamingStrategy.SnakeCase),
				businessId);
	}

	/**
	 * 将数据推送到 Web
	 *
	 * @param data       数据
	 * @param businessId 业务id
	 */
	private void pushDataToWeb(String data, String businessId) {

		String templateId = evalTaskMapper.getTemplateIdByTaskId(businessId);
		EvalTemplate evalTemplate = evalTemplateService.getById(templateId);

		if (evalTemplate != null) {
			WebSocketMessageContext messageContext = crEventListenerHelper.makeContext(data,
					templateId);
			webSocketHandler.pushMessageToUsers(messageContext,
					Lists.newArrayList(evalTemplate.getCreateUserId()));
		}

	}

}
