package com.cyberpeace.cr.evaluation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.evaluation.dao.entity.EvalAction;
import com.cyberpeace.cr.evaluation.dao.entity.EvalActionTemplate;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalActionMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalActionTemplateMapper;
import com.cyberpeace.cr.evaluation.service.EvalActionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EvalActionServiceImpl extends ServiceImpl<EvalActionMapper, EvalAction> implements EvalActionService {

	@Autowired
	private EvalActionTemplateMapper evalActionTemplateMapper;

	@Override
	public void createByTemplate(String cloudTemplateId, String testId, Map<String, String> ruleIdMap) {
		List<EvalActionTemplate> actionTemplateList = evalActionTemplateMapper.selectByCloudTemplateId(cloudTemplateId);
		List<EvalAction> evalActionList = actionTemplateList.stream().map(actionTemplate -> {
			EvalAction evalAction = BeanUtils.copy(actionTemplate, EvalAction.class);
			evalAction.setId(null);
			evalAction.setResourceId(null);
			evalAction.setTestId(testId);
			evalAction.setRuleId(ruleIdMap.get(actionTemplate.getResourceId()));
			return evalAction;
		}).collect(Collectors.toList());
		saveBatch(evalActionList);
	}
}
