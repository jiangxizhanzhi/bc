package com.cyberpeace.rtks.service;

import java.util.Date;
import java.util.List;

import com.cyberpeace.rtks.dao.entity.SkillAndKnowledgeBase;
import com.cyberpeace.rtks.service.bo.KnowledgeStudyPercentBo;
import com.cyberpeace.rtks.service.bo.RoleStudyBo;
import com.cyberpeace.rtks.service.bo.RtksRadarBo;
import com.cyberpeace.rtks.service.bo.StudyRecordBo;
import com.cyberpeace.rtks.service.bo.situation.UserStatisticsBo;
import com.cyberpeace.rtks.service.bo.skill.MissionPathBo;
import com.cyberpeace.rtks.service.bo.skill.SkillTrainingRecordBo;

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
	Integer roleMatchPercent(String roleId, String userId);

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
	 * 针对知识点，获取用户理论课学习记录
	 * @param roleId
	 * @param userId
	 * @return
	 */
	RoleStudyBo roleKnowledgeStudyRecord(String roleId, String userId);

	/**
	 * 统计用户岗位课程学习记录
	 * @param userId
	 * @param roleId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	StudyRecordBo calculateStudyRecord(String userId, String roleId, Date startTime, Date endTime);

	/**
	 * 查询当前用户的训练路径
	 * @param userId
	 * @param roleId
	 * @return
	 */
	List<MissionPathBo> getTrainingPath(String userId, String roleId);

	/**
	 * 统计 个人中心-岗位评估-岗位技能训练记录
	 * @param userId
	 * @param roleId
	 * @param toDate1
	 * @param toDate11
	 * @return
	 */
	SkillTrainingRecordBo calculateTrainingRecord(String userId, String roleId, Date toDate1,
			Date toDate11);

	/**
	 * 获取知识完成度
	 *
	 * @param knowledge 知识id
	 * @param userId      用户id
	 * @return {@code Integer}
	 */
	Integer getKnowledgeProgress(SkillAndKnowledgeBase knowledge, String userId);

	/**
	 * 获取用户统计数据
	 *
	 * @param userId 用户id
	 * @param sort 排序字段
	 * @param asc 正序？
	 * @return {@code UserTrend}
	 */
	List<UserStatisticsBo> getUserStatistics(String userId, String sort, Boolean asc);
}
