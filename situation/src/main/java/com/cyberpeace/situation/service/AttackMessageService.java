package com.cyberpeace.situation.service;

import com.cyberpeace.situation.controller.req.AttackMessageReq;
import com.cyberpeace.situation.controller.req.UpdateAttackMessageReq;

public interface AttackMessageService {

    String addAttackMessage(AttackMessageReq attackMessageReq);

    void updateAttackMessage(UpdateAttackMessageReq updateAttackMessageReq);
}
