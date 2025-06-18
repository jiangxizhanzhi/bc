package com.cyberpeace.rtks.service;

import java.util.Date;
import java.util.List;

import com.cyberpeace.oj.course.service.bo.CourseStudyProcessBo;
import com.cyberpeace.rtks.service.bo.KnowledgeStudyPercentBo;
import com.cyberpeace.rtks.service.bo.RoleStudyBo;
import com.cyberpeace.rtks.service.bo.RtksRadarBo;
import com.cyberpeace.rtks.service.bo.StudyRecordBo;
import com.cyberpeace.rtks.service.bo.skill.MissionPathBo;
import com.cyberpeace.rtks.service.bo.skill.SkillTrainingRecordBo;

/**
 * rtks数据统计功能
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
public interface RtksStatisticService {

	/**
	 * 计算指定岗位的匹配度
	 *
	 * @param roleId
	 * @param userId
	 * @return 匹配度百分比
	 * @throws
	 * @see String
	 * @see String
	 * @see Integer
	 */
	Integer roleMatchPercent(String roleId, String userId);

	/**
	 * 计算岗位雷达图
	 *
	 * @param roleId
	 * @param userId
	 * @return
	 * @throws
	 * @see String
	 * @see String
	 * @see List<RtksRadarBo>
	 */
	List<RtksRadarBo> calculateRadar(String roleId, String userId);

	/**
	 * 计算用户指定角色的知识点完成度
	 *
	 * @param roleId
	 * @param userId
	 * @return
	 * @throws
	 * @see String
	 * @see String
	 * @see List<KnowledgeStudyPercentBo>
	 */
	List<KnowledgeStudyPercentBo> calculateKnowledgePercent(String roleId, String userId);

	/**
	 * 针对知识点，获取用户理论课学习记录
	 *
	 * @param roleId
	 * @param userId
	 * @return
	 * @throws
	 * @see String
	 * @see String
	 * @see RoleStudyBo
	 */
	RoleStudyBo roleKnowledgeStudyRecord(String roleId, String userId);

	/**
	 * 统计用户岗位课程学习记录
	 *
	 * @param userId
	 * @param roleId
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws
	 * @see String
	 * @see String
	 * @see Date
	 * @see Date
	 * @see StudyRecordBo
	 */
	StudyRecordBo calculateStudyRecord(String userId, String roleId, Date startTime, Date endTime);

	/**
	 * 查询当前用户的训练路径
	 *
	 * @param userId
	 * @param roleId
	 * @return
	 * @throws
	 * @see String
	 * @see String
	 * @see List<MissionPathBo>
	 */
	List<MissionPathBo> getTrainingPath(String userId, String roleId);

	/**
	 * 统计 个人中心-岗位评估-岗位技能训练记录
	 *
	 * @param userId
	 * @param roleId
	 * @param toDate1
	 * @param toDate11
	 * @return
	 * @throws
	 * @see String
	 * @see String
	 * @see Date
	 * @see Date
	 * @see SkillTrainingRecordBo
	 */
	SkillTrainingRecordBo calculateTrainingRecord(String userId, String roleId, Date toDate1,
			Date toDate11);

	/**
	 * 根据课程进度列表计算岗位学习进度
	 *
	 * @param processBoList
	 * @return
	 * @throws
	 * @see List<CourseStudyProcessBo>
	 * @see RoleStudyBo
	 */
	RoleStudyBo getStudyBo(List<CourseStudyProcessBo> processBoList);
}
