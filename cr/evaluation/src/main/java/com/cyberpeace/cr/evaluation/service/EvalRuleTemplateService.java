package com.cyberpeace.cr.evaluation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.cr.evaluation.controller.req.*;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalRuleMPRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalRuleTemplateDetailRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalRuleTemplateEnumRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalRuleTemplateListRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalRuleTemplate;

import java.util.List;

public interface EvalRuleTemplateService extends IService<EvalRuleTemplate> {

	EvalRuleTemplateDetailRsp addRuleTemplate(EvalRuleTemplateAddReq evalRuleTemplateAddReq);

	List<EvalRuleTemplateListRsp> getRuleTemplateList(EvalRuleTemplateListReq evalRuleTemplateListReq);

	void deleteRuleTemplate(DeleteReq deleteReq);

	/**
	 * 获取规则模板详情
	 * @param id
	 * @return
	 */
	EvalRuleTemplateDetailRsp getRuleTemplate(String id);

	EvalRuleTemplateDetailRsp modifyRuleTemplate(EvalRuleTemplateModifyReq evalRuleTemplateModifyReq);

	EvalRuleTemplateEnumRsp getRuleEnum();

	List<EvalRuleMPRsp> getRuleList(String cloudTemplateId);
}
