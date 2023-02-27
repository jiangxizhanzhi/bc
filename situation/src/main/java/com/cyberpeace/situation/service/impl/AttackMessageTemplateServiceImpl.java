package com.cyberpeace.situation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.situation.controller.req.AttackMessageTemplateReq;
import com.cyberpeace.situation.dao.entity.AttackMessageTemplate;
import com.cyberpeace.situation.dao.mapper.AttackMessageTemplateMapper;
import com.cyberpeace.situation.service.AttackMessageTemplateService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AttackMessageTemplateServiceImpl extends ServiceImpl<AttackMessageTemplateMapper, AttackMessageTemplate> implements AttackMessageTemplateService {

	@Override
	public void bashAddAttackMessageTemplate(List<AttackMessageTemplateReq> attackMessageTemplateReqList) {
		List<AttackMessageTemplate> attackMessageTemplateList = new ArrayList<>();
		for (AttackMessageTemplateReq attackMessageTemplateReq : attackMessageTemplateReqList) {
			attackMessageTemplateReq.setResourceId(IDGeneratorUtil.getID());
			attackMessageTemplateList.add(BeanUtils.copy(attackMessageTemplateReq, AttackMessageTemplate.class));
		}
		saveBatch(attackMessageTemplateList);
	}

	@Override
	public void bashUpdateAttackMessageTemplate(List<AttackMessageTemplateReq> attackMessageTemplateReqList) {
		List<AttackMessageTemplate> attackMessageTemplateList = new ArrayList<>();
		for (AttackMessageTemplateReq attackMessageTemplateReq : attackMessageTemplateReqList) {
			attackMessageTemplateList.add(BeanUtils.copy(attackMessageTemplateReq, AttackMessageTemplate.class));
		}
		updateBatchById(attackMessageTemplateList);
	}
}
