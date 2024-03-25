package com.cyberpeace.cr.evaluation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.dao.entity.EvalDimension;

import java.util.Map;

public interface EvalDimensionService extends IService<EvalDimension> {

	void createByTemplate(String cloudTemplateId, String testId, Map<String, String> ruleIdMap);

}
