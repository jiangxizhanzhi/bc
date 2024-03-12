package com.cyberpeace.cr.evaluation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.controller.req.EvalStepTemplateAddReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalStepTemplateModifyReq;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalStepTemplateEnumRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalStepTemplateListRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalStepTemplate;

import java.util.List;

public interface EvalStepTemplateService extends IService<EvalStepTemplate> {

	String addStep(EvalStepTemplateAddReq evalStepTemplateAddReq);

	void deleteStep(List<String> ids);

	void modifyStep(EvalStepTemplateModifyReq evalStepTemplateModifyReq);

	List<EvalStepTemplateListRsp> getStepList(String cloudTemplateId);

	EvalStepTemplateEnumRsp getStepEnum();

}
