package com.cyberpeace.situation.service;

import com.cyberpeace.situation.controller.req.AttackMessageReq;

import java.util.List;

public interface AttackMessageService {

	String addAttackMessage(AttackMessageReq attackMessageReq);

	void updateAttackMessage(AttackMessageReq attackMessageReq);

	void bathAddAttackMessage(List<AttackMessageReq> attackMessageReqList);

	void bathUpdateAttackMessage(List<AttackMessageReq> attackMessageReqList);
}
