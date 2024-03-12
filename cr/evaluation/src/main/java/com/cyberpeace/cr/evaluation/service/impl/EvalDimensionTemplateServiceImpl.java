package com.cyberpeace.cr.evaluation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.EnumUtils;
import com.cyberpeace.cr.crbase.controller.rsp.constants.CodeNameRsp;
import com.cyberpeace.cr.evaluation.controller.req.EvalDimensionAddReq;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalDimensionEnumRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalDimensionTemplate;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalDimensionTemplateMapper;
import com.cyberpeace.cr.evaluation.service.EvalDimensionTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EvalDimensionTemplateServiceImpl extends ServiceImpl<EvalDimensionTemplateMapper, EvalDimensionTemplate> implements EvalDimensionTemplateService {

	@Autowired
	private EvalDimensionTemplateMapper evalDimensionTemplateMapper;

	@Override
	public String addDimensionTemplate(EvalDimensionAddReq evalDimensionAddReq) {
		EvalDimensionTemplate evalDimensionTemplate = BeanUtils.copy(evalDimensionAddReq, EvalDimensionTemplate.class);
		evalDimensionTemplate.setType(EvalDimensionTemplate.Type.HOST.getCode());
		EvalDimensionTemplate.Operate operate = EnumUtils.getEnumByCode(evalDimensionAddReq.getOperate(), EvalDimensionTemplate.Operate.class);
		evalDimensionTemplate.setRuleParameter(operate, evalDimensionAddReq.getObject(), evalDimensionAddReq.getValue());
		evalDimensionTemplateMapper.insert(evalDimensionTemplate);
		return evalDimensionTemplate.getResourceId();
	}

	@Override
	public EvalDimensionEnumRsp getDimensionEnum() {
		EvalDimensionEnumRsp evalDimensionEnumRsp = new EvalDimensionEnumRsp();
		evalDimensionEnumRsp.setOperation(Arrays.stream(EvalDimensionTemplate.Operate.values())
												.map(operate -> new CodeNameRsp(operate.getCode(), operate.getDesc())).collect(Collectors.toList()));
		evalDimensionEnumRsp.setEventType(Arrays.stream(EvalDimensionTemplate.EventType.values())
												.map(eventType -> new CodeNameRsp(eventType.getCode(), eventType.getDesc())).collect(Collectors.toList()));
		evalDimensionEnumRsp.setType(Arrays.stream(EvalDimensionTemplate.Type.values())
										   .map(type -> new CodeNameRsp(type.getCode(), type.getDesc())).collect(Collectors.toList()));
		return evalDimensionEnumRsp;
	}


}
