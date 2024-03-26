package com.cyberpeace.cr.evaluation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.evaluation.controller.req.EvalHostTemplateAddReq;
import com.cyberpeace.cr.evaluation.dao.entity.EvalHostTemplate;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalHostTemplateMapper;
import com.cyberpeace.cr.evaluation.service.EvalHostTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EvalHostTemplateServiceImpl extends ServiceImpl<EvalHostTemplateMapper, EvalHostTemplate> implements EvalHostTemplateService {

	@Autowired
	private EvalHostTemplateMapper evalHostTemplateMapper;

	@Override
	public String addHost(EvalHostTemplateAddReq evalHostTemplateAddReq) {
		EvalHostTemplate evalHostTemplate = BeanUtils.copy(evalHostTemplateAddReq, EvalHostTemplate.class);
		evalHostTemplateMapper.insert(evalHostTemplate);
		return evalHostTemplate.getResourceId();
	}

	@Override
	public void deleteHost(List<String> ids) {
		evalHostTemplateMapper.deleteBatchIds(ids);
	}

	@Override
	public void addHostTemplateList(List<EvalHostTemplateAddReq> evalHostTemplateAddReqList, String cloudTemplateId, String ruleId) {
		if (CollectionUtils.isEmpty(evalHostTemplateAddReqList)) {
			return;
		}
		List<EvalHostTemplate> evalHostTemplateList = evalHostTemplateAddReqList.stream().map(evalHostTemplateAddReq -> {
			EvalHostTemplate evalHostTemplate = BeanUtils.copy(evalHostTemplateAddReq, EvalHostTemplate.class);
			evalHostTemplate.setCloudTemplateId(cloudTemplateId);
			evalHostTemplate.setRuleId(ruleId);
			return evalHostTemplate;
		}).collect(Collectors.toList());
		saveBatch(evalHostTemplateList);
	}
}
