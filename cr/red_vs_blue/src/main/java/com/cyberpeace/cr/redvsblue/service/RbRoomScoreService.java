package com.cyberpeace.cr.redvsblue.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.redvsblue.dao.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.service.context.TimeData;
import com.cyberpeace.cr.redvsblue.service.context.XYData;
import com.cyberpeace.cr.redvsblue.service.context.mission.RoleScore;

/**
 * 红蓝对抗房间得分Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/18
 * @see IService
 * @since 1.0.0
 */
public interface RbRoomScoreService extends IService<RbRoom> {

	/**
	 * 获取角色得分数据
	 *
	 * @param roomId 红蓝对抗实例id
	 * @return 角色得分数据
	 * @throws
	 * @see String
	 * @see List<RoleScore>
	 */
	List<RoleScore> getRoleScoreInfo(String roomId);

	/**
	 * 获取时间趋势数据
	 *
	 * @param roomId 红蓝对抗实例id
	 * @return 时间趋势数据
	 * @throws
	 * @see String
	 * @see TimeData
	 */
	TimeData getRbTimeData(String roomId);

	/**
	 * 获取任务类别图表数据
	 *
	 * @param roomId   红蓝对抗实例id
	 * @param roleType 角色类型
	 * @return 任务类别图表数据
	 * @throws
	 * @see String
	 * @see Integer
	 * @see XYData
	 */
	XYData getRbMissionCategory(String roomId, Integer roleType);

	/**
	 * 获取红蓝学员排名
	 *
	 * @param roomId   红蓝对抗实例id
	 * @param roleType 角色类型
	 * @return 红蓝学员排名
	 * @throws
	 * @see String
	 * @see Integer
	 * @see JSONObject
	 */
	JSONObject getRbMembersRanking(String roomId, Integer roleType);

	/**
	 * 获取红蓝对抗实例的任务评估数据统计
	 *
	 * @param roomId   红蓝对抗实例id
	 * @param roleType 角色类型
	 * @return 任务评估数据统计
	 * @throws
	 * @see String
	 * @see Integer
	 * @see JSONObject
	 */
	JSONObject getRbMissionEvaluation(String roomId, Integer roleType);

	/**
	 * 获取任务排名数据
	 *
	 * @param roomId   红蓝对抗实例id
	 * @param roleType 角色类型
	 * @return 任务排名数据
	 * @throws
	 * @see String
	 * @see Integer
	 * @see JSONObject
	 */
	JSONObject getRbLeaderBoard(String roomId, Integer roleType);

	/**
	 * 获取任务进度信息
	 *
	 * @param roomId   红蓝对抗实例id
	 * @param roleType 角色类型
	 * @return 任务进度数据
	 * @throws
	 * @see String
	 * @see Integer
	 * @see JSONObject
	 */
	JSONObject getRbMissionProgress(String roomId, Integer roleType);

	/**
	 * 获取得分趋势信息
	 *
	 * @param roomId   红蓝对抗实例id
	 * @param roleType 角色类型
	 * @return 得分趋势信息
	 * @throws
	 * @see String
	 * @see Integer
	 * @see JSONObject
	 */
	JSONObject getRbScoreProgress(String roomId, Integer roleType);

	/**
	 * 获取所有角色的得分趋势
	 *
	 * @param roomId   红蓝对抗实例id
	 * @param roleType 岗位类型
	 * @return 红蓝双方得分趋势
	 * @throws
	 * @see String
	 * @see Integer
	 * @see JSONObject
	 */
	JSONObject getAllRbScoreProgress(String roomId, Integer roleType);
}
