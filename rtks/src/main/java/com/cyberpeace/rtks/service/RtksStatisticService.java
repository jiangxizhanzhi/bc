package com.cyberpeace.rtks.service;

import java.util.List;

import com.cyberpeace.rtks.service.bo.CategorySkillBo;
import com.cyberpeace.rtks.service.bo.KnowledgeStudyPercentBo;
import com.cyberpeace.rtks.service.bo.RoleStudyBo;
import com.cyberpeace.rtks.service.bo.RtksRadarBo;

/**
 * rtks数据统计功能
 */
public interface RtksStatisticService {

	/**
	 * 计算指定岗位的匹配度
	 * @param roleId
	 * @param userId
	 * @return 匹配度百分比
	 */
	String roleMatchPercent(String roleId, String userId);

	/**
	 * 计算岗位雷达图
	 * @param roleId
	 * @param userId
	 * @return
	 */
	List<RtksRadarBo> calculateRadar(String roleId, String userId);

	/**
	 * 计算用户指定角色的知识点完成度
	 * @param roleId
	 * @param userId
	 * @return
	 */
	List<KnowledgeStudyPercentBo> calculateKnowledgePercent(String roleId, String userId);

	/**
	 * 计算制定角色的技能完成度
	 * @param roleId
	 * @param userId
	 * @return
	 */
	List<KnowledgeStudyPercentBo> calculateSkillPercent(String roleId, String userId);

	/**
	 * 针对知识点，获取用户理论课学习记录
	 * @param roleId
	 * @param userId
	 * @return
	 */
	RoleStudyBo roleKnowledgeStudyRecord(String roleId, String userId);

	/**
	 * 针对技能，获取用户的对应的学习记录
	 * @param roleId
	 * @param userId
	 * @return
	 */
	RoleStudyBo roleSkillStudyRecord(String roleId, String userId);

	/**
	 * 根据用户编号和岗位编号统计岗位下技能得分
	 * @param userId
	 * @param roleId
	 * @return
	 */
	List<CategorySkillBo> getRoleSkillScores(String userId, String roleId);

	/**
	 * 计算岗位下技能能力雷达图
	 * @param userId
	 * @param roleId
	 * @return
	 */
    List<RtksRadarBo> calculateSkillRadar(String userId, String roleId);
}
