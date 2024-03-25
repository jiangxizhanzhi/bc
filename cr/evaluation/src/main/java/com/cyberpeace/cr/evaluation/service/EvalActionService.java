package com.cyberpeace.cr.evaluation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.dao.entity.EvalAction;

import java.util.Map;

public interface EvalActionService extends IService<EvalAction> {

	void createByTemplate(String cloudTemplateId, String testId, Map<String, String> ruleIdMap);

}
