package com.cyberpeace.cr.evaluation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.cr.evaluation.dao.entity.EvalRule;
import com.cyberpeace.cr.evaluation.dao.entity.EvalRuleTemplate;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalRuleMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalRuleTemplateMapper;
import com.cyberpeace.cr.evaluation.service.EvalActionService;
import com.cyberpeace.cr.evaluation.service.EvalDimensionService;
import com.cyberpeace.cr.evaluation.service.EvalRuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EvalRuleServiceImpl extends ServiceImpl<EvalRuleMapper, EvalRule> implements EvalRuleService {

	@Autowired
	private EvalRuleMapper evalRuleMapper;

	@Autowired
	private EvalRuleTemplateMapper evalRuleTemplateMapper;

	@Autowired
	private EvalActionService evalActionService;

	@Autowired
	private EvalDimensionService evalDimensionService;

	@Override
	public void createByTemplate(String cloudTemplateId, String testId, Map<String, String> stepIdMap) {
		List<EvalRuleTemplate> ruleTemplateList = evalRuleTemplateMapper.selectAllByCloudTemplateId(cloudTemplateId);
		Map<String, String> ruleIdMap = new HashMap<>();
		List<EvalRule>	evalRuleList = ruleTemplateList.stream().map(evalRuleTemplate -> {
			EvalRule rule = BeanUtils.copy(evalRuleTemplate, EvalRule.class);
			String id = IDGeneratorUtil.getID();
			rule.setResourceId(id);
			ruleIdMap.put(evalRuleTemplate.getResourceId(), id);
			rule.setId(null);
			rule.setTestId(testId);
			List<String> stepIdList = new ArrayList<>();
			rule.setStepIdList(stepIdList);
			return rule;
		}).collect(Collectors.toList());
		evalActionService.createByTemplate(cloudTemplateId, testId, ruleIdMap);
		evalDimensionService.createByTemplate(cloudTemplateId, testId, ruleIdMap);
		saveBatch(evalRuleList);
	}






}
