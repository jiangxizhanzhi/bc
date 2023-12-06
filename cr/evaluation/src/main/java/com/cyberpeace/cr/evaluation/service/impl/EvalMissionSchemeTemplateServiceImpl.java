package com.cyberpeace.cr.evaluation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.cr.evaluation.dao.entity.EvalMissionSchemeTemplate;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalMissionSchemeTemplateMapper;
import com.cyberpeace.cr.evaluation.service.EvalMissionSchemeTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
