package com.cyberpeace.cr.evaluation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.cr.evaluation.dao.entity.EvalStep;
import com.cyberpeace.cr.evaluation.dao.entity.EvalStepTemplate;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalStepMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalStepTemplateMapper;
import com.cyberpeace.cr.evaluation.service.EvalStepService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EvalStepServiceImpl extends ServiceImpl<EvalStepMapper, EvalStep> implements EvalStepService {

	@Autowired
	private EvalStepMapper evalStepMapper;

	@Autowired
	private EvalStepTemplateMapper evalStepTemplateMapper;

	@Override
	public Map<String, String> createByTemplate(List<String> templateIdList, String testId) {
		if (CollectionUtils.isEmpty(templateIdList)) {
			return new HashMap<>();
		}
		List<EvalStepTemplate> stepTemplateList = evalStepTemplateMapper.selectBatchIds(templateIdList);
		Map<String, EvalStepTemplate> stepTemplateMap = stepTemplateList.stream()
																		.collect(Collectors.toMap(EvalStepTemplate::getResourceId, evalStepTemplate -> evalStepTemplate));
		List<EvalStep> ruleList = new ArrayList<>();
		Map<String, String> stepIdMap = new HashMap<>();
		for (String resourceId : templateIdList) {
			EvalStepTemplate evalStepTemplate = stepTemplateMap.get(resourceId);
			EvalStep evalStep = new EvalStep();
			evalStep.setTestId(testId);
			evalStep.setName(evalStepTemplate.getName());
			evalStep.setProcess(evalStepTemplate.getProcess());
			evalStep.setSetting(evalStepTemplate.getSetting());
			String newId = IDGeneratorUtil.getID();
			evalStep.setResourceId(newId);
			stepIdMap.put(resourceId, newId);
			ruleList.add(evalStep);
		}
		saveBatch(ruleList);
		return stepIdMap;
	}




}
