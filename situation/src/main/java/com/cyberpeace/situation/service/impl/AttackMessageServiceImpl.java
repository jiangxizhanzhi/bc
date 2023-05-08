package com.cyberpeace.situation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.situation.constant.topo.AttackType;
import com.cyberpeace.situation.controller.req.AttackMessageReq;
import com.cyberpeace.situation.dao.entity.AttackMessage;
import com.cyberpeace.situation.dao.mapper.AttackMessageMapper;
import com.cyberpeace.situation.service.AttackMessageService;

import java.util.ArrayList;
import java.util.List;

@Service
public class AttackMessageServiceImpl extends ServiceImpl<AttackMessageMapper,AttackMessage> implements AttackMessageService {

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

	@Override
	public void bathAddAttackMessage(List<AttackMessageReq> attackMessageReqList){
		List<AttackMessage> attackMessageList = new ArrayList<>();
		for(AttackMessageReq attackMessageReq:attackMessageReqList){
			attackMessageReq.setResourceId(IDGeneratorUtil.getID());
			attackMessageList.add(attackMessageBoToAttackMessage(attackMessageReq));
		}
		saveBatch(attackMessageList);
	}

	@Override
	public void bathUpdateAttackMessage(List<AttackMessageReq> attackMessageReqList){
		List<AttackMessage> attackMessageList = new ArrayList<>();
		for (AttackMessageReq attackMessageReq:attackMessageReqList){
			attackMessageList.add(attackMessageBoToAttackMessage(attackMessageReq));
		}
		updateBatchById(attackMessageList);
	}

	public AttackMessage attackMessageBoToAttackMessage(AttackMessageReq attackMessageReq) {
		AttackMessage attackMessage = BeanUtils.copy(attackMessageReq, AttackMessage.class);
		attackMessage
				.setMediaAnimation(JSON.toJSONString(attackMessageReq.getMediaAnimationList()));
		if (!attackMessageReq.getType().equals(AttackType.MEDIA_ANIMATION.getKeyWord())) {
			attackMessage.setMediaAnimation(null);
		}
		return attackMessage;
	}
}
