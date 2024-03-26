package com.cyberpeace.cr.evaluation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.controller.req.EvalHostTemplateAddReq;
import com.cyberpeace.cr.evaluation.dao.entity.EvalHostTemplate;

import java.util.List;

public interface EvalHostTemplateService extends IService<EvalHostTemplate> {

	String addHost(EvalHostTemplateAddReq evalHostTemplateAddReq);

	void deleteHost(List<String> ids);

	void addHostTemplateList(List<EvalHostTemplateAddReq> evalHostTemplateAddReqList, String cloudTemplateId, String ruleId);
}
