package com.cyberpeace.cr.evaluation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.service.context.mission.MissionSchemeStage;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalMissionSchemeTemplateListRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalMissionSchemeTemplate;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalMissionSchemeTemplateMapper;
import com.cyberpeace.cr.evaluation.service.EvalMissionSchemeTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EvalMissionSchemeTemplateServiceImpl extends ServiceImpl<EvalMissionSchemeTemplateMapper, EvalMissionSchemeTemplate> implements EvalMissionSchemeTemplateService {

	@Autowired
	private EvalMissionSchemeTemplateMapper evalMissionSchemeTemplateMapper;

	@Override
	public void addEvalMissionSchemeTemplate(EvalMissionSchemeTemplate evalMissionSchemeTemplate) {
		evalMissionSchemeTemplateMapper.insert(evalMissionSchemeTemplate);
	}

	@Override
	public EvalMissionSchemeTemplate getEvalMissionSchemeTemplate(String resourceId) {
		EvalMissionSchemeTemplate evalMissionSchemeTemplate = evalMissionSchemeTemplateMapper.selectById(resourceId);
		if (evalMissionSchemeTemplate == null) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		return evalMissionSchemeTemplate;
	}

	@Override
	public void deleteEvalMissionSchemeTemplate(List<String> ids) {
		evalMissionSchemeTemplateMapper.deleteBatchIds(ids);
	}

	@Override
	public void updateEvalMissionSchemeTemplate(EvalMissionSchemeTemplate evalMissionSchemeTemplate) {
		evalMissionSchemeTemplateMapper.updateById(evalMissionSchemeTemplate);
	}

	@Override
	public List<EvalMissionSchemeTemplateListRsp> getEvalMissionSchemeTemplateList(PageReqBase pageReqBase) {
		List<EvalMissionSchemeTemplate> evalMissionSchemeTemplateList = evalMissionSchemeTemplateMapper.getTemplateList(pageReqBase.getPattern());
		return evalMissionSchemeTemplateList.stream()
											.map(evalMissionSchemeTemplate -> {
												EvalMissionSchemeTemplateListRsp evalMissionSchemeTemplateListRsp = BeanUtils.copy(evalMissionSchemeTemplate, EvalMissionSchemeTemplateListRsp.class);
												List<MissionSchemeStage> missionSchemeStageList = JsonUtil.toJsonObjectArray(evalMissionSchemeTemplate.getTemplateData(), MissionSchemeStage.class);
												evalMissionSchemeTemplateListRsp.setStageCount(missionSchemeStageList.size());
												int taskCount = 0;
												for (MissionSchemeStage missionSchemeStage : missionSchemeStageList) {
													taskCount += missionSchemeStage.getTemplates().size();
												}
												evalMissionSchemeTemplateListRsp.setTaskCount(taskCount);
												return evalMissionSchemeTemplateListRsp;
											}).collect(Collectors.toList());
	}
}
