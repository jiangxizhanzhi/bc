package com.cyberpeace.cr.evaluation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.component.MyWebSocketHandler;
import com.cyberpeace.common.model.scene.MqContext;
import com.cyberpeace.common.model.ws.WebSocketMessageContext;
import com.cyberpeace.cr.crbase.service.impl.CrEventListenerHelper;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTask;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTemplate;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalTaskMapper;
import com.cyberpeace.cr.evaluation.service.EvalTaskService;
import com.cyberpeace.cr.evaluation.service.EvalTemplateService;
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

		EvalTask evalTask = evalTaskMapper.selectByIdAndStatus(businessId,
				EvalTask.Status.RUNNING.getCode());

		if (evalTask != null) {
			evalTaskService.stopTask(evalTask);
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
