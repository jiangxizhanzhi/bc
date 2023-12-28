package com.cyberpeace.rtks.service;

import com.cyberpeace.rtks.service.bo.KnowledgeStudyPercentBo;
import com.cyberpeace.rtks.service.bo.RoleStudyBo;
import com.cyberpeace.rtks.service.bo.RtksRadarBo;
import com.cyberpeace.rtks.service.bo.StudyRecordBo;
import com.cyberpeace.rtks.service.bo.skill.SkillTrainingRecordBo;

import java.util.Date;
import java.util.List;

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
	 * @param userId
	 * @return
	 */
	RoleStudyBo roleKnowledgeStudyRecord( String userId);

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
	 * 统计 个人中心-岗位评估-岗位技能训练记录
	 * @param userId
	 * @param roleId
	 * @param toDate1
	 * @param toDate11
	 * @return
	 */
	SkillTrainingRecordBo calculateTrainingRecord(String userId, String roleId, Date toDate1,
			Date toDate11);
}
