package com.cyberpeace.cr.crbase.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.crbase.controller.rsp.StudentScoreRsp;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.entity.CrScore;
import com.cyberpeace.cr.crbase.service.context.TrainReportContext;
import com.cyberpeace.cr.crbase.service.context.mission.MissionsStatistics;
import com.cyberpeace.cr.crbase.service.context.score.StudentScoreListRsp;

/**
 *
 */
public interface CrScoreService extends IService<CrScore> {

	/** 获取学员的成绩
	 * @param roomId 训练实例id
	 * @param userId 用户id
	 * @return 学员成绩
	 */
	CrScore getByRoomIdAndUser(String roomId, String userId);

	/** 获取任务统计
	 * @param roomId 训练实例id
	 * @param crRoleList 角色列表
	 * @return 任务统计
	 */
	MissionsStatistics getMissionsStatistics(String roomId, List<CrRole> crRoleList);

	/**
	 * 批量获取某训练的学员成绩
	 * @param roomId  训练实例id
	 * @param userIds 学员id列表
	 * @return 学员成绩列表
	 */
	List<CrScore> selectByRoomIdAndUserIds(String roomId, List<String> userIds);

	/**
	 * 获取训练中角色拥有的任务的类别统计
	 * @param roomId 训练实例id
	 * @param crRoleList 角色列表
	 * @return 任务类别统计
	 */
	Map<String, Integer> getMissionsCategory(String roomId, List<CrRole> crRoleList);

	/**
	 * 获取得分列表
	 *
	 * @param roomId 房间id
	 * @return {@code List<StudentScoreListRsp>}
	 */
	List<CrScore> getScoreByRoomId(String roomId);

	/**
	 * 获取评分列表
	 *
	 * @param roomId   房间id
	 * @param roleList 角色列表
	 * @return {@code List<StudentScoreListRsp>}
	 */
	List<StudentScoreListRsp> getScoreListRsp(String roomId, List<CrRole> roleList);

	/**
	 * 获取学员评分
	 *
	 * @param roomId 房间id
	 * @param userId 用户id
	 * @return {@code StudentScoreRsp}
	 */
	StudentScoreRsp getStudentScore(String roomId, String userId);

	TrainReportContext getTrainReport(String resource_id, ArrayList<Integer> roles);

}
