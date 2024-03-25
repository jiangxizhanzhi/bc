package com.cyberpeace.cr.evaluation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.cr.evaluation.dao.entity.EvalDimension;
import com.cyberpeace.cr.evaluation.dao.entity.EvalDimensionTemplate;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalDimensionMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalDimensionTemplateMapper;
import com.cyberpeace.cr.evaluation.service.EvalDimensionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EvalDimensionServiceImpl extends ServiceImpl<EvalDimensionMapper, EvalDimension> implements EvalDimensionService {

	@Autowired
	private EvalDimensionMapper evalDimensionMapper;

	@Autowired
	private EvalDimensionTemplateMapper evalDimensionTemplateMapper;

	public void createByTemplate(String cloudTemplateId, String testId, Map<String, String> ruleIdMap) {
		List<EvalDimensionTemplate> dimensionTemplateList = evalDimensionTemplateMapper.selectByCloudTemplateId(cloudTemplateId);
		List<EvalDimension> evalDimensionList = dimensionTemplateList.stream().map(dimensionTemplate -> {
			EvalDimension evalDimension = BeanUtils.copy(dimensionTemplate, EvalDimension.class);
			evalDimension.setId(null);
			evalDimension.setRuleId(ruleIdMap.get(dimensionTemplate.getRuleId()));
			evalDimension.setResourceId(IDGeneratorUtil.getID());
			evalDimension.setTestId(testId);
			return evalDimension;
		}).collect(Collectors.toList());
		saveBatch(evalDimensionList);
	}
}
