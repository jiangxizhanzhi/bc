package com.cyberpeace.cr.evaluation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.dao.entity.EvalRule;

import java.util.Map;

public interface EvalRuleService extends IService<EvalRule> {

	void createByTemplate(String cloudTemplateId, String testId, Map<String, String> taskIdMap);

}
