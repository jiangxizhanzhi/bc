package com.cyberpeace.situation.service;

import com.cyberpeace.situation.controller.req.AttackMessageReq;

import java.util.List;

/**
 * 攻击消息Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
public interface AttackMessageService {

	/**
	 * 新增攻击消息
	 *
	 * @param attackMessageReq 攻击消息请求体
	 * @return {@code String }
	 * @throws
	 * @see AttackMessageReq
	 * @see String
	 */
	String addAttackMessage(AttackMessageReq attackMessageReq);

	/**
	 * 更新攻击消息
	 *
	 * @param attackMessageReq 攻击消息请求体
	 * @throws
	 * @see AttackMessageReq
	 */
	void updateAttackMessage(AttackMessageReq attackMessageReq);

	/**
	 * 浴新增攻击消息
	 *
	 * @param attackMessageReqList 攻击消息请求体列表
	 * @throws
	 * @see List<AttackMessageReq>
	 */
	void bathAddAttackMessage(List<AttackMessageReq> attackMessageReqList);

	/**
	 * Bath Update 攻击消息
	 *
	 * @param attackMessageReqList 攻击消息请求体列表
	 * @throws
	 * @see List<AttackMessageReq>
	 */
	void bathUpdateAttackMessage(List<AttackMessageReq> attackMessageReqList);
}
