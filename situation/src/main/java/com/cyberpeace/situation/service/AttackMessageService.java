package com.cyberpeace.situation.service;

import com.cyberpeace.situation.controller.req.AttackMessageReq;

public interface AttackMessageService {

	String addAttackMessage(AttackMessageReq attackMessageReq);

	void updateAttackMessage(AttackMessageReq attackMessageReq);
}
