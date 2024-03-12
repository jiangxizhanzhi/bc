package com.cyberpeace.cr.evaluation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.controller.req.EvalDimensionAddReq;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalDimensionEnumRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalDimensionTemplate;

public interface EvalDimensionTemplateService extends IService<EvalDimensionTemplate> {

	String addDimensionTemplate(EvalDimensionAddReq evalDimensionAddReq);

	EvalDimensionEnumRsp getDimensionEnum();

}
