package com.cyberpeace.cr.evaluation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.evaluation.controller.req.EvalStepTemplateAddReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalStepTemplateModifyReq;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalStepTemplateListRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalStepTemplate;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalStepTemplateMapper;
import com.cyberpeace.cr.evaluation.service.EvalStepTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EvalStepTemplateServiceImpl extends ServiceImpl<EvalStepTemplateMapper, EvalStepTemplate> implements EvalStepTemplateService {

	@Autowired
	private EvalStepTemplateMapper evalStepTemplateMapper;

	@Override
	public String addStep(EvalStepTemplateAddReq evalStepTemplateAddReq) {
		EvalStepTemplate evalStepTemplate = BeanUtils.copy(evalStepTemplateAddReq, EvalStepTemplate.class);
		evalStepTemplate.setSettingList(evalStepTemplateAddReq.getSet());
		evalStepTemplateMapper.insert(evalStepTemplate);
		return evalStepTemplate.getResourceId();
	}

	@Override
	public void deleteStep(List<String> ids) {
		evalStepTemplateMapper.deleteBatchIds(ids);
	}

	@Override
	public void modifyStep(EvalStepTemplateModifyReq evalStepTemplateModifyReq) {
		EvalStepTemplate evalStepTemplate = BeanUtils.copy(evalStepTemplateModifyReq, EvalStepTemplate.class);
		evalStepTemplate.setResourceId(evalStepTemplateModifyReq.getStepId());
		evalStepTemplate.setSettingList(evalStepTemplateModifyReq.getSet());
		evalStepTemplateMapper.updateById(evalStepTemplate);
	}

	@Override
	public List<EvalStepTemplateListRsp> getStepList(String cloudTemplateId) {
		List<EvalStepTemplateListRsp> evalStepTemplateListRspList = new ArrayList<>();
		List<EvalStepTemplate> evalStepTemplateList = evalStepTemplateMapper.selectStepByCloudTemplateId(cloudTemplateId);
		Map<String, EvalStepTemplate> evalStepTemplateMap = evalStepTemplateList.stream()
																				.collect(Collectors.toMap(EvalStepTemplate::getResourceId, evalStepTemplate -> evalStepTemplate));
		List<String> stepTemplateIds = JsonUtil.toJsonObjectArray(evalStepTemplateMapper.selectIdListByCloudTemplateId(cloudTemplateId), String.class);
		for (String stepTemplateId : stepTemplateIds) {
			EvalStepTemplate evalStepTemplate = evalStepTemplateMap.get(stepTemplateId);
			EvalStepTemplateListRsp evalStepTemplateListRsp = BeanUtils.copy(evalStepTemplate, EvalStepTemplateListRsp.class);
			evalStepTemplateListRsp.setStepId(stepTemplateId);
			evalStepTemplateListRsp.setSet(evalStepTemplate.getSettingList());
			evalStepTemplateListRspList.add(evalStepTemplateListRsp);
		}
		return evalStepTemplateListRspList;
	}

}
