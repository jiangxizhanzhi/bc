package com.cyberpeace.situation.service;

import com.cyberpeace.situation.constant.ws.AttackType;
import com.cyberpeace.situation.controller.req.AttackMessageReq;
import com.cyberpeace.situation.controller.res.AttackMessageHistoryRes;
import com.cyberpeace.situation.model.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public interface AttackMessageHistoryService {

	/**
	 * 新增动效历史
	 * @param attackMessageReq 动效数据
	 */
	void add(AttackMessageReq attackMessageReq, Boolean attackSuccess);

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
	 * @return 不同状态的主机数
	 */
	List<HostStatusCount> getHostStatusCount(String sceneId);

	/**
	 * 将损毁的主机恢复
	 * @param sceneId 场景编号
	 */
	void recovery(String sceneId);

	/**
	 * 获取攻击日志
	 * @param sceneId 场景编号
	 * @return 攻击日志
	 */
	List<AttackLog> getAttackLog(String sceneId);

	/**
	 * 获取运行时间
	 * @param sceneId 场景编号
	 * @return 返回运行时长
	 */
	Map<String, Integer> getIntervalTime(String sceneId);

	/**
	 * 获取防御历史
	 * @param sceneId 场景编号
	 * @return 防御历史
	 */
	List<DefenseLog> getDefenseLog(String sceneId);

	/**
	 * 推送瘫痪节点列表
	 * @param sceneId 场景编号
	 */
	void pushParalysisNodeStatus(String sceneId);

	/**
	 * 获取防御趋势
	 * @param sceneId 场景编号
	 * @return 防御趋势
	 */
	List<SceneTrend> getDefenseTrend(String sceneId);

	/**
	 * 清理因节点删除导致的脏数据
	 * @param sceneId 场景编号
	 */
	void clearIllegalData(String sceneId);

	<T> void pushDataTo28S(String topoId, T data);

	List<AttackMessageHistoryRes> getAttackMessageHistoryRes (String sceneId, Date startTime, Date endTime);
}
