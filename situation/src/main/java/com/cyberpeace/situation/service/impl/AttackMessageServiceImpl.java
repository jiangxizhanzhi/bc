package com.cyberpeace.situation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.situation.constant.ws.AttackType;
import com.cyberpeace.situation.controller.req.AttackMessageReq;
import com.cyberpeace.situation.dao.entity.AttackMessage;
import com.cyberpeace.situation.dao.mapper.AttackMessageMapper;
import com.cyberpeace.situation.service.AttackMessageService;

import java.util.ArrayList;
import java.util.List;

/**
 * 攻击消息Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see ServiceImpl
 * @see AttackMessageService
 * @since 1.0.0
 */
@Service
public class AttackMessageServiceImpl extends ServiceImpl<AttackMessageMapper,AttackMessage> implements AttackMessageService {

	/**
	 * 攻击消息Mapper
	 *
	 * @see AttackMessageMapper
	 */
	@Autowired
	private AttackMessageMapper attackMessageMapper;

	/**
	 * 新增攻击消息
	 *
	 * @param attackMessageReq 攻击消息请求体
	 * @return {@code String }
	 * @throws
	 * @see AttackMessageReq
	 * @see String
	 */
	@Override
	public String addAttackMessage(AttackMessageReq attackMessageReq) {
		AttackMessage attackMessage = attackMessageBoToAttackMessage(attackMessageReq);
		attackMessage.setResourceId(IDGeneratorUtil.getID());
		attackMessageMapper.insert(attackMessage);
		return attackMessage.getResourceId();
	}

	/**
	 * 更新攻击消息
	 *
	 * @param attackMessageReq 攻击消息请求体
	 * @throws
	 * @see AttackMessageReq
	 */
	@Override
	public void updateAttackMessage(AttackMessageReq attackMessageReq) {
		AttackMessage attackMessage = attackMessageBoToAttackMessage(attackMessageReq);
		attackMessageMapper.updateById(attackMessage);
	}

	/**
	 * 浴新增攻击消息
	 *
	 * @param attackMessageReqList 攻击消息请求体列表
	 * @throws
	 * @see List<AttackMessageReq>
	 */
	@Override
	public void bathAddAttackMessage(List<AttackMessageReq> attackMessageReqList){
		List<AttackMessage> attackMessageList = new ArrayList<>();
		for(AttackMessageReq attackMessageReq:attackMessageReqList){
			attackMessageReq.setResourceId(IDGeneratorUtil.getID());
			attackMessageList.add(attackMessageBoToAttackMessage(attackMessageReq));
		}
		saveBatch(attackMessageList);
	}

	/**
	 * Bath Update 攻击消息
	 *
	 * @param attackMessageReqList 攻击消息请求体列表
	 * @throws
	 * @see List<AttackMessageReq>
	 */
	@Override
	public void bathUpdateAttackMessage(List<AttackMessageReq> attackMessageReqList){
		List<AttackMessage> attackMessageList = new ArrayList<>();
		for (AttackMessageReq attackMessageReq:attackMessageReqList){
			attackMessageList.add(attackMessageBoToAttackMessage(attackMessageReq));
		}
		updateBatchById(attackMessageList);
	}

	/**
	 * 攻击消息Bo自攻击消息
	 *
	 * @param attackMessageReq 攻击消息请求体
	 * @return {@code AttackMessage }
	 * @throws
	 * @see AttackMessageReq
	 * @see AttackMessage
	 */
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
