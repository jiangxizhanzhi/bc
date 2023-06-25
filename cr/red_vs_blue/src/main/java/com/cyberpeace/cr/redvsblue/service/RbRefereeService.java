package com.cyberpeace.cr.redvsblue.service;

import java.util.List;

import com.cyberpeace.cr.crbase.service.context.evaluation.EvaluationListReq;
import com.cyberpeace.cr.crbase.service.context.evaluation.EvaluationListRsp;
import com.cyberpeace.cr.crbase.service.context.scene.MonitorSession;
import com.cyberpeace.cr.crbase.service.context.score.StudentScoreListRsp;
import com.cyberpeace.cr.redvsblue.service.context.evaluation.RbRoleIdNameType;

/**
 * rb裁判Service
 *
 * @author SN
 * @date 2023/05/18
 */
public interface RbRefereeService {
    /**
     * 获取评估列表
     *
     * @param req 请求体
     * @return {@code List<EvaluationListRsp>}
     */
    List<EvaluationListRsp> getEvaluationList(EvaluationListReq req);

    /**
     * 获取评分列表
     *
     * @param roomId 房间id
     * @return {@code List<StudentScoreListRsp>}
     */
    List<StudentScoreListRsp> getScoreList(String roomId);

    /**
     * 获取角色列表，用以评估列表角色下拉单
     *
     * @param roomId 房间id
     * @return {@code List<RbRoleIdNameType>}
     */
    List<RbRoleIdNameType> getRoleList(String roomId);

	/**
	 * 学员提交任务后通知裁判
	 *
	 * @param missionId 任务id
	 */
	void notifySubmission(String missionId);

	/**
	 * 获取监控会话
	 *
	 * @param roomId 房间id
	 * @return {@code List<MonitorSession>}
	 */
	List<MonitorSession> getMonitorSessions(String roomId);
}
