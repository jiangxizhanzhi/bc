package com.cyberpeace.situation.service;

import com.cyberpeace.situation.constant.ws.AttackType;
import com.cyberpeace.situation.controller.req.AttackMessageReq;
import com.cyberpeace.situation.model.*;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public interface AttackMessageHistoryService {

	/**
	 * 新增动效历史
	 * @param attackMessageReq 动效数据
	 */
	void add(AttackMessageReq attackMessageReq);

	/**
	 * 获取当前场景攻击手段Top5
	 * @param sceneId 场景编号
	 * @return 攻击手段数据
	 */
	List<AttackTypeCount> getAttackTop5(String sceneId);

	/**
	 * 获取当前场景防御手段Top5
	 * @param sceneId 场景编号
	 * @return 防御手段数据
	 */
	List<DefenseTypeCount> getDefenseTop5(String sceneId);

	/**
	 * 获取主机Top5
	 * @param filterValue
	 * @param sceneId 场景id
	 * @return
	 */
	List<HostCount> getHostTop5(Predicate<? super AttackType> filterValue , String sceneId);

	/**
	 * 获取不同状态的主机数
	 * @param sceneId 场景id
	 * @return
	 */
	List<HostStatusCount> getHostStatusCount(String sceneId);

	void recovery(String sceneId);

	List<AttackLog> getAttackLogTop5(String sceneId);

	Map<String, Integer> getIntervalTime(String sceneId);

	List<AttackMessageReq> getAttackHistory(String sceneId);
}
