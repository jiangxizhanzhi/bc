package com.cyberpeace.situation.service;

import com.cyberpeace.situation.controller.req.AttackMessageTemplateReq;

import java.util.List;

public interface AttackMessageTemplateService {

	void bashAddAttackMessageTemplate(List<AttackMessageTemplateReq> attackMessageTemplateReqList);

	void bashUpdateAttackMessageTemplate(List<AttackMessageTemplateReq> attackMessageTemplateReqList);

}
