package com.cyberpeace.cr.evaluation.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.cyberpeace.common.model.AuthTable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.cyberpeace.common.component.MyWebSocketHandler;
import com.cyberpeace.common.constant.WebConstant;
import com.cyberpeace.common.context.scene.ElementFlow;
import com.cyberpeace.common.enums.BusinessCode;
import com.cyberpeace.common.model.scene.MqContext;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.model.ws.WebSocketMessageContext;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.service.CrSceneService;
import com.cyberpeace.cr.crbase.service.context.scene.CrSceneWsMessageIdentifier;
import com.cyberpeace.cr.crbase.service.context.scene.FlowUpdate;
import com.cyberpeace.cr.crbase.service.impl.CrEventListenerHelper;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTemplate;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalTaskMapper;
import com.cyberpeace.cr.evaluation.service.EvalTaskService;
import com.cyberpeace.cr.evaluation.service.EvalTemplateService;
import com.cyberpeace.cr.evaluation.service.context.EvalTaskOpsContext;
import com.cyberpeace.cs.constant.Operator;
import com.cyberpeace.cs.service.context.FlowUpdateContext;
import com.cyberpeace.cs.service.context.WsMessageBO;
import com.cyberpeace.cs.service.impl.MessageListener;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EvalSceneEventListener extends MessageListener {

	@Autowired
	private MyWebSocketHandler webSocketHandler;

	@Autowired
	private CrEventListenerHelper crEventListenerHelper;

	@Autowired
	private CrSceneService crSceneService;

	@Autowired
	private EvalTaskService evalTaskService;

	@Autowired
	private EvalTemplateService evalTemplateService;

	@Autowired
	private EvalTaskMapper evalTaskMapper;

	private List<String> flowCodes = Lists.newArrayList("node_start_flow", "node_stop_flow");

	@Override
	public Integer declareMessageType() {
		return BusinessCode.RANGE_EVALUATION.getCode();
	}

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

	@Override
	public Boolean onSceneStartProcess(String data, String businessId, MqContext context) {
		pushDataToWeb(data, businessId);
		return true;
	}

	@Override
	public Boolean onSceneElement(String data, String businessId, MqContext context) {

		JSONObject message = JsonUtil.toJsonObject(data, JSONObject.class).getJSONObject("data")
				.getJSONObject("data");
		String callAction = message.getString("call_action");

		if (callAction != null && flowCodes.contains(callAction)) {
			processFlowMessage(context, businessId);
		}

		pushDataToWeb(data, businessId);
		return true;
	}

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

	private void pushDataToWeb(String data, String businessId) {

		String templateId = evalTaskMapper.getTemplateIdByTaskId(businessId);
		EvalTemplate evalTemplate = evalTemplateService.getById(templateId);

		if (evalTemplate != null) {
			WebSocketMessageContext<CrSceneWsMessageIdentifier, Object> messageContext = crEventListenerHelper
					.makeContext(data, templateId);
			if (AuthTable.AuthType.ALL_SHARE_MODE.getCode().equals(evalTemplate.getOperationAuthType())) {
				webSocketHandler.pushMessage(messageContext,
						true);
			} else {
				ArrayList<String> userIds = Lists.newArrayList(JsonUtil.toJsonObjectArray(evalTemplate.getOperationAuthIds(), String.class));
				userIds.add(evalTemplate.getCreateUserId());
				webSocketHandler.pushMessageToUsers(messageContext,
						userIds);
			}

		}

	}

}
