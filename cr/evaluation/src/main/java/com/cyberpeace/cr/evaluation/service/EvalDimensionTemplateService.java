package com.cyberpeace.cr.evaluation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.controller.req.EvalDimensionAddReq;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalDimensionEnumRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalDimensionTemplate;

import java.util.List;

public interface EvalDimensionTemplateService extends IService<EvalDimensionTemplate> {

	void addDimensionTemplateList(List<EvalDimensionAddReq> evalDimensionAddReqList, String cloudTemplateId, String ruleId);

	EvalDimensionEnumRsp getDimensionEnum();

}
