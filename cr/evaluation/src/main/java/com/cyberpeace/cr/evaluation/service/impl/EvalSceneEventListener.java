package com.cyberpeace.cr.evaluation.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.component.MyWebSocketHandler;
import com.cyberpeace.common.constant.WebConstant;
import com.cyberpeace.common.model.scene.MqContext;
import com.cyberpeace.common.model.ws.WebSocketMessageContext;
import com.cyberpeace.cr.crbase.service.impl.CrEventListenerHelper;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTask;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTemplate;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalTaskMapper;
import com.cyberpeace.cr.evaluation.service.EvalTaskService;
import com.cyberpeace.cr.evaluation.service.EvalTemplateService;
import com.cyberpeace.cr.evaluation.service.context.EvalTaskOperContext;
import com.cyberpeace.cs.constant.Operator;
import com.cyberpeace.cs.dao.entity.LocalScene;
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
	private EvalTaskService evalTaskService;

	@Autowired
	private EvalTemplateService evalTemplateService;

	@Autowired
	private EvalTaskMapper evalTaskMapper;

	@Override
	public Integer declareMessageType() {
		return LocalScene.BusinessCode.RANGE_EVALUATION.getCode();
	}

	@Override
	public Boolean onSceneDelete(String data, String businessId, MqContext context) {

		// 判断删除为靶场执行时，跳过后续操作
		String operator = JSON.parseObject(context.getPassThrough(), JSONObject.class)
				.getString(WebConstant.PASS_THROUGH_OPERATOR);
		if (StringUtils.isNotEmpty(operator) && operator.equals(Operator.BC.getDesc())) {
			return true;
		}

		EvalTaskOperContext evalTaskOperContext = evalTaskService.getEvalTaskOperContext(businessId,
				false);

		EvalTask evalTask = evalTaskOperContext.getEvalTask();

		if (evalTask != null) {
			evalTaskService.deleteTask(evalTaskOperContext);
		}

		return true;
	}

	@Override
	public Boolean onSceneStartProcess(String data, String businessId, MqContext context) {
		pushDataToWeb(data, businessId);
		return true;
	}

	@Override
	public Boolean onSceneElement(String data, String businessId, MqContext context) {
		pushDataToWeb(data, businessId);
		return true;
	}

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
