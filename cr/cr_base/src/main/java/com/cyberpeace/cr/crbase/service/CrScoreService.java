package com.cyberpeace.cr.crbase.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.crbase.controller.rsp.StudentScoreRsp;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.entity.CrScore;
import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import com.cyberpeace.cr.crbase.dao.entity.Mission;
import com.cyberpeace.cr.crbase.service.context.TrainReportContext;
import com.cyberpeace.cr.crbase.service.context.mission.MissionsStatistics;
import com.cyberpeace.cr.crbase.service.context.score.ScoreTrendReq;
import com.cyberpeace.cr.crbase.service.context.score.StudentScoreListRsp;
import com.cyberpeace.cr.crbase.service.context.score.UserSkillScoreContext;

/**
 * 靶场得分Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see IService
 * @since 1.0.0
 */
public interface CrScoreService extends IService<CrScore> {

	/**
	 * 获取学员的成绩
	 *
	 * @param roomId 训练实例id
	 * @param userId 用户id
	 * @return 学员成绩
	 * @throws
	 * @see String
	 * @see String
	 * @see CrScore
	 */
	CrScore getByRoomIdAndUser(String roomId, String userId);

	/**
	 * 获取任务统计
	 *
	 * @param roomId     训练实例id
	 * @param crRoleList 角色列表
	 * @return 任务统计
	 * @throws
	 * @see String
	 * @see List<CrRole>
	 * @see MissionsStatistics
	 */
	MissionsStatistics getMissionsStatistics(String roomId, List<CrRole> crRoleList);

	/**
	 * 批量获取某训练的学员成绩
	 *
	 * @param roomId  训练实例id
	 * @param userIds 学员id列表
	 * @return 学员成绩列表
	 * @throws
	 * @see String
	 * @see List<String>
	 * @see List<CrScore>
	 */
	List<CrScore> selectByRoomIdAndUserIds(String roomId, List<String> userIds);

	/**
	 * 获取训练中角色拥有的任务的类别统计
	 *
	 * @param roomId     训练实例id
	 * @param crRoleList 角色列表
	 * @return 任务类别统计
	 * @throws
	 * @see String
	 * @see List<CrRole>
	 * @see Map<String, Integer>
	 */
	Map<String, Integer> getMissionsCategory(String roomId, List<CrRole> crRoleList);

	/**
	 * 获取得分列表
	 *
	 * @param roomId 房间id
	 * @return {@code List<StudentScoreListRsp>}
	 * @throws
	 * @see String
	 * @see List<CrScore>
	 */
	List<CrScore> getScoreByRoomId(String roomId);

	/**
	 * 获取评分列表
	 *
	 * @param roomId   房间id
	 * @param roleList 角色列表
	 * @return {@code List<StudentScoreListRsp>}
	 * @throws
	 * @see String
	 * @see List<CrRole>
	 * @see List<StudentScoreListRsp>
	 */
	List<StudentScoreListRsp> getScoreListRsp(String roomId, List<CrRole> roleList);

	/**
	 * 获取学员评分
	 *
	 * @param roomId 房间id
	 * @param userId 用户id
	 * @return {@code StudentScoreRsp}
	 * @throws
	 * @see String
	 * @see String
	 * @see StudentScoreRsp
	 */
	StudentScoreRsp getStudentScore(String roomId, String userId);

	/**
	 * 获取训练报告
	 *
	 * @param resource_id   资源id
	 * @param roles         角色
	 * @param scoreTrendReq 得分趋势请求体
	 * @return {@code TrainReportContext }
	 * @throws
	 * @see String
	 * @see ArrayList<Integer>
	 * @see ScoreTrendReq
	 * @see TrainReportContext
	 */
	TrainReportContext getTrainReport(String resource_id, ArrayList<Integer> roles, ScoreTrendReq scoreTrendReq);

	/**
	 * 获取技能得分列表
	 *
	 * @param roomId 房间id
	 * @param roles  角色
	 * @return {@code List<UserSkillScoreContext> }
	 * @throws
	 * @see String
	 * @see ArrayList<Integer>
	 * @see List<UserSkillScoreContext>
	 */
	List<UserSkillScoreContext> getSkillScoreList(String roomId,ArrayList<Integer> roles);

	/**
	 * 获取技能得分列表
	 *
	 * @param roomId     房间id
	 * @param userIds    用户id列表
	 * @param taskList   任务列表
	 * @param missionMap 任务图
	 * @return {@code List<UserSkillScoreContext> }
	 * @throws
	 * @see String
	 * @see List<String>
	 * @see List<CrTask>
	 * @see Map<String, Mission>
	 * @see List<UserSkillScoreContext>
	 */
	List<UserSkillScoreContext> getSkillScoreList(String roomId,List<String> userIds, List<CrTask> taskList, Map<String, Mission> missionMap);
}
