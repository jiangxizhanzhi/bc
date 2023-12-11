package com.cyberpeace.cr.evaluation.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.enums.BusinessCode;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.service.impl.bo.SceneConfigData;
import com.cyberpeace.cr.evaluation.controller.req.EvalTemTemplateReq;
import com.cyberpeace.cr.evaluation.controller.req.UpdateEvalTemTemplateReq;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTemTemplate;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalTemTemplateMapper;
import com.cyberpeace.cr.evaluation.service.EvalSchemeTemplateService;
import com.cyberpeace.cr.evaluation.service.EvalTemTemplateService;
import com.cyberpeace.cr.evaluation.service.impl.bo.MissionScheme;
import com.cyberpeace.cs.service.LocalSceneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

@Service
public class EvalTemTemplateServiceImpl extends ServiceImpl<EvalTemTemplateMapper, EvalTemTemplate> implements EvalTemTemplateService {

	@Autowired
	private EvalTemTemplateMapper evalTemTemplateMapper;

	@Autowired
	private LocalSceneService localSceneService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private EvalSchemeTemplateService evalSchemeTemplateService;

	@Override
	public String createTemplate(EvalTemTemplateReq evalTemTemplateReq) {
		EvalTemTemplate evalTemTemplate = BeanUtils.copy(evalTemTemplateReq, EvalTemTemplate.class);
		evalTemTemplate.setMissionScheme(JsonUtil.toJsonString(evalTemTemplateReq.getMissionsScheme()));
		evalTemTemplate.setCreateUserId(UserInfoContext.getUserContext().getUserId());
		evalTemTemplate.setTopoId(saveTopo(evalTemTemplateReq.getSceneConfigData()));
		evalTemTemplateMapper.insert(evalTemTemplate);
		return evalTemTemplate.getResourceId();
	}

	@Override
	public void updateTemplate(UpdateEvalTemTemplateReq updateEvalTemTemplateReq) {
		EvalTemTemplate evalTemTemplate = evalTemTemplateMapper.selectById(updateEvalTemTemplateReq.getResourceId());
		MissionScheme oldScheme = JsonUtil.toJsonObject(evalTemTemplate.getMissionScheme(), MissionScheme.class);
		List<String> oldResourceIds = evalSchemeTemplateService.getTaskIds(oldScheme);
		List<String> newResourceIds = evalSchemeTemplateService.getTaskIds(updateEvalTemTemplateReq.getMissionsScheme());
		oldResourceIds.removeAll(newResourceIds);
		// todo
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				evalSchemeTemplateService.deleteEvalSchemeTemplate(oldResourceIds);

			}
		});
	}

	private String saveTopo(SceneConfigData sceneConfigData) {
		JSONObject topoJson = new JSONObject();
		topoJson.put("scene_config", sceneConfigData.getSceneConfig());
		topoJson.put("logo", sceneConfigData.getLogo());
		topoJson.put("support_partial_start", sceneConfigData.getSupportPartialStart());
		return localSceneService.saveTopo(topoJson, BusinessCode.RANGE_EVALUATION.getCode());
	}


}
