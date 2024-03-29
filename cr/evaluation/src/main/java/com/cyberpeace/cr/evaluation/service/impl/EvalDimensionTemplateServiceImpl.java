package com.cyberpeace.cr.evaluation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.crbase.controller.rsp.constants.CodeNameRsp;
import com.cyberpeace.cr.evaluation.controller.req.EvalDimensionAddReq;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalDimensionEnumRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalDimensionTemplate;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalDimensionTemplateMapper;
import com.cyberpeace.cr.evaluation.service.EvalDimensionTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EvalDimensionTemplateServiceImpl extends ServiceImpl<EvalDimensionTemplateMapper, EvalDimensionTemplate> implements EvalDimensionTemplateService {

	@Override
	public void addDimensionTemplateList(List<EvalDimensionAddReq> evalDimensionAddReqList, String cloudTemplateId, String ruleId) {
		if (CollectionUtils.isEmpty(evalDimensionAddReqList)) {
			return;
		}
		List<EvalDimensionTemplate> dimensionTemplateList = evalDimensionAddReqList.stream().map(evalDimensionAddReq -> {
			EvalDimensionTemplate evalDimensionTemplate = BeanUtils.copy(evalDimensionAddReq, EvalDimensionTemplate.class);
			evalDimensionTemplate.setCloudTemplateId(cloudTemplateId);
			evalDimensionTemplate.setRuleId(ruleId);
			evalDimensionTemplate.setType(EvalDimensionTemplate.Type.HOST.getCode());
			return evalDimensionTemplate;
		}).collect(Collectors.toList());
		saveBatch(dimensionTemplateList);
	}

	@Override
	public EvalDimensionEnumRsp getDimensionEnum() {
		EvalDimensionEnumRsp evalDimensionEnumRsp = new EvalDimensionEnumRsp();
		evalDimensionEnumRsp.setEventType(Arrays.stream(EvalDimensionTemplate.EventType.values())
												.map(eventType -> new CodeNameRsp(eventType.getCode(), eventType.getDesc())).collect(Collectors.toList()));
		evalDimensionEnumRsp.setType(Arrays.stream(EvalDimensionTemplate.Type.values())
										   .map(type -> new CodeNameRsp(type.getCode(), type.getDesc())).collect(Collectors.toList()));
		return evalDimensionEnumRsp;
	}


}
