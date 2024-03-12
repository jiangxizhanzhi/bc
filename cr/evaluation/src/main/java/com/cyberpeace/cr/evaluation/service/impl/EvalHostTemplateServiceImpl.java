package com.cyberpeace.cr.evaluation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.evaluation.controller.req.EvalHostAddReq;
import com.cyberpeace.cr.evaluation.dao.entity.EvalHostTemplate;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalHostTemplateMapper;
import com.cyberpeace.cr.evaluation.service.EvalHostTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class EvalHostTemplateServiceImpl extends ServiceImpl<EvalHostTemplateMapper, EvalHostTemplate> implements EvalHostTemplateService {

	@Autowired
	private EvalHostTemplateMapper evalHostTemplateMapper;

	@Override
	public String addHost(EvalHostAddReq evalHostAddReq) {
		EvalHostTemplate evalHostTemplate = BeanUtils.copy(evalHostAddReq, EvalHostTemplate.class);
		evalHostTemplateMapper.insert(evalHostTemplate);
		return evalHostTemplate.getResourceId();
	}

	@Override
	public void deleteHost(List<String> ids) {
		evalHostTemplateMapper.deleteBatchIds(ids);
	}
}
