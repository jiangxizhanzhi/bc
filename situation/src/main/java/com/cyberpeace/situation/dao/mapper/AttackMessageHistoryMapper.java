package com.cyberpeace.situation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.situation.dao.entity.AttackMessageHistory;
import com.cyberpeace.situation.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface AttackMessageHistoryMapper extends BaseMapper<AttackMessageHistory> {

	/**
	 * 获取攻击类型统计
	 * @param sceneId 场景编号
	 * @return 攻击类型统计
	 */
	List<AttackTypeCount> getAttackTop(String sceneId);

	/**
	 * 获取防御类型统计
	 * @param sceneId 场景编号
	 * @return 防御类型统计
	 */
	List<DefenseTypeCount> getDefenseTop(String sceneId);

	/**
	 * 获取主机状态统计
	 * @param sceneId 场景编号
	 * @param attackTypeList 动效类型列表
	 * @return 主机状态统计
	 */
	List<HostCount> getHostTop(@Param("sceneId") String sceneId,@Param("attackTypeList") List<String> attackTypeList);

	/**
	 * 获取指定的主机状态统计
	 * @param sceneId 场景编号
	 * @param targetId 目标节点ID
	 * @param attackTypeList 动效类型列表
	 * @return
	 */
	Integer getStateCount(@Param("sceneId") String sceneId, @Param("targetId") String targetId,
			@Param("attackTypeList") List<String> attackTypeList);

	/**
	 * 恢复
	 * @param sceneId 场景编号
	 */
	void recovery(@Param("sceneId") String sceneId);

	/**
	 * 获取攻击日志
	 * @param sceneId 场景编号
	 * @return 攻击日志
	 */
	List<AttackLog> getAttackLog(@Param("sceneId") String sceneId);

	/**
	 * 获取最近5次攻击日志
	 * @param sceneId 场景编号
	 * @param targetId 目标节点ID
	 * @return
	 */
	List<AttackInfo> getLast5AttackLog(@Param("sceneId") String sceneId,
			@Param("targetId") String targetId);

	/**
	 * 获取场景运行时间
	 * @param sceneId 场景编号
	 * @return 运行时间
	 */
	String getStartTime(@Param("sceneId") String sceneId);

	/**
	 * 获取所有动效历史
	 * @param sceneId 场景编号
	 * @return 动效历史列表
	 */
	List<AttackMessageHistory> getAttackHistoryByTime(@Param("sceneId") String sceneId, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

	/**
	 * 获取防御历史
	 * @param sceneId 场景编号
	 * @return 防御日志
	 */
	List<DefenseLog> getDefenseLog(@Param("sceneId") String sceneId);

	/**
	 * 获取当前节点防御等级为高的防御动效历史
	 */
	List<AttackMessageHistory> getDynamicByNodeIdByHighDefenseLevel(@Param("defenseTargetId") String defenseTargetId);

	void deleteMessageBySceneId(@Param("sceneId") String sceneId);

}
