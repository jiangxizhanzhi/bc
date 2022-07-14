package com.cyberpeace.situation.service.impl;

import com.alibaba.fastjson.JSON;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.situation.controller.req.AttackMessageReq;
import com.cyberpeace.situation.controller.req.UpdateAttackMessageReq;
import com.cyberpeace.situation.dao.entity.AttackMessage;
import com.cyberpeace.situation.dao.mapper.AttackMessageMapper;
import com.cyberpeace.situation.service.AttackMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttackMessageServiceImpl implements AttackMessageService {

    @Autowired
    private AttackMessageMapper attackMessageMapper;

    @Override
    public String addAttackMessage(AttackMessageReq attackMessageReq) {
        AttackMessage attackMessage = attackMessageBoToAttackMessage(attackMessageReq);
        attackMessage.setResourceId(IDGeneratorUtil.getID());
        attackMessageMapper.insert(attackMessage);
        return attackMessage.getResourceId();
    }

    @Override
    public void updateAttackMessage(AttackMessageReq attackMessageReq) {
        AttackMessage attackMessage = attackMessageBoToAttackMessage(attackMessageReq);
        attackMessageMapper.updateById(attackMessage);
    }

    public AttackMessage attackMessageBoToAttackMessage(AttackMessageReq attackMessageBo){
        AttackMessage attackMessage = BeanUtils.copy(attackMessageBo,AttackMessage.class);
//        attackMessage.setLineStyle(JSON.toJSONString(attackMessageBo.getLineStyle()));
        attackMessage.setMediaAnimation(JSON.toJSONString(attackMessageBo.getMediaAnimationList()));
        return attackMessage;
    }
}
