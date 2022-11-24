package com.cyberpeace.situation.service;

import com.cyberpeace.situation.controller.req.AttackMessageReq;

import java.util.List;

public interface AttackMessageService {

	/**
	 * 新增动效数据
	 * @param attackMessageReq 动效数据
	 * @return 动效编号
	 */
	String addAttackMessage(AttackMessageReq attackMessageReq);

	/**
	 * 更新动效数据
	 * @param attackMessageReq 动效数据
	 */
	void updateAttackMessage(AttackMessageReq attackMessageReq);

	/**
	 * 批量新增动效数据
	 * @param attackMessageReqList 动效数据列表
	 */
	void bathAddAttackMessage(List<AttackMessageReq> attackMessageReqList);

	/**
	 * 批量更新动效数据
	 * @param attackMessageReqList 动效数据列表
	 */
	void bathUpdateAttackMessage(List<AttackMessageReq> attackMessageReqList);
}
