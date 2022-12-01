package com.cyberpeace.situation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.situation.dao.entity.AttackMessageHistory;
import com.cyberpeace.situation.model.AttackLog;
import com.cyberpeace.situation.model.AttackTypeCount;
import com.cyberpeace.situation.model.DefenseTypeCount;
import com.cyberpeace.situation.model.HostCount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
	List<AttackMessageHistory> getAllAttackHistory(@Param("sceneId") String sceneId);

}
