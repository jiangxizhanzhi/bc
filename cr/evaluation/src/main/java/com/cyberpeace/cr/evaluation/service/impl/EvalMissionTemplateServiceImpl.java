package com.cyberpeace.cr.evaluation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.evaluation.controller.req.EvalMissionTemplateReq;
import com.cyberpeace.cr.evaluation.controller.req.UpdateEvalMissionTemplateReq;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalMissionTemplateRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalMissionTemplate;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalMissionTemplateMapper;
import com.cyberpeace.cr.evaluation.service.EvalMissionTemplateService;
import com.cyberpeace.cr.evaluation.service.impl.bo.MissionScheme;
import com.cyberpeace.cr.evaluation.service.impl.bo.StageData;
import com.cyberpeace.cr.evaluation.service.impl.bo.TaskBo;
import com.cyberpeace.cr.evaluation.service.impl.bo.ToolBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EvalMissionTemplateServiceImpl extends ServiceImpl<EvalMissionTemplateMapper, EvalMissionTemplate> implements EvalMissionTemplateService {

	@Autowired
	private EvalMissionTemplateMapper evalMissionTemplateMapper;

	@Override
	public String addMissionTemplate(EvalMissionTemplateReq evalMissionTemplateReq) {
		EvalMissionTemplate evalMissionTemplate = BeanUtils.copy(evalMissionTemplateReq, EvalMissionTemplate.class);
		evalMissionTemplate.setTools(JsonUtil.toJsonString(evalMissionTemplateReq.getTools()));
		evalMissionTemplateMapper.insert(evalMissionTemplate);
		return evalMissionTemplate.getResourceId();
	}

	@Override
	public void updateMissionTemplate(UpdateEvalMissionTemplateReq updateEvalMissionReq) {
		EvalMissionTemplate evalMissionTemplate = BeanUtils.copy(updateEvalMissionReq, EvalMissionTemplate.class);
		evalMissionTemplate.setTools(JsonUtil.toJsonString(updateEvalMissionReq.getTools()));
		evalMissionTemplateMapper.updateById(evalMissionTemplate);
	}

	@Override
	public EvalMissionTemplateRsp getMissionTemplate(String resourceId) {
		EvalMissionTemplate evalMissionTemplate = evalMissionTemplateMapper.selectById(resourceId);
		EvalMissionTemplateRsp evalMissionTemplateRsp = BeanUtils.copy(evalMissionTemplate, EvalMissionTemplateRsp.class);
		evalMissionTemplateRsp.setTools(JsonUtil.toJsonObjectArray(evalMissionTemplate.getTools(), ToolBo.class));
		return evalMissionTemplateRsp;
	}

	@Override
	public void deleteMissionTemplate(List<String> resourceIds) {
		evalMissionTemplateMapper.deleteBatchIds(resourceIds);
	}

	@Override
	public List<String> getTaskIds(List<MissionScheme> missionSchemeList) {
		List<String> resourceIds = new ArrayList<>();
		for (MissionScheme missionScheme : missionSchemeList) {
			for (StageData stageData : missionScheme.getStageList()) {
				resourceIds.addAll(stageData.getTaskList().stream().map(TaskBo::getResourceId).collect(Collectors.toList()));
			}
		}
		return resourceIds;
	}
}
