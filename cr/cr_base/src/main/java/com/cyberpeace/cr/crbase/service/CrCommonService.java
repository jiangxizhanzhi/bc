package com.cyberpeace.cr.crbase.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.cr.crbase.dao.entity.CrMark;
import com.cyberpeace.cr.crbase.service.context.mark.MarkAddReq;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfig;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfigMissionsScheme;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfigRb;
import com.cyberpeace.cr.crbase.service.context.scene.MonitorSession;

public interface CrCommonService {

	/**
	 * 获取监控会话
	 * @param localSceneId 本地场景id
	 * @param studentId 学员id
	 * @return 监控列表
	 */
	List<MonitorSession> getMonitorSessions(String localSceneId, String studentId);

	/**
	 * 判断中台scene是否已经停止
	 * @param sceneData 场景数据
	 * @return 场景是否停止
	 */
	Boolean isStopped(JSONObject sceneData);

	/**
	 * 校验前端输入的 role config
	 * @param roleConfigs 角色配置字符串
	 * @return 任务模板id列表
	 */
	List<String> validateRoleConfig(List<CrRoleConfig> roleConfigs);

	/**
	 * 校验前端输入的 missions scheme
	 * @param missionsScheme 角色任务配置
	 * @return 返回校验过的 mission template id 列表
	 */
	List<String> validateMissionsScheme(CrRoleConfigMissionsScheme missionsScheme);

	/**
	 * 校验前端输入的红蓝角色配置
	 * @param roleConfigRb 红蓝角色配置
	 */
	void validateRbRoleConfig(CrRoleConfigRb roleConfigRb);

	/**
	 * 新增标记
	 *
	 * @param req  请求体
	 * @param code 代码
	 */
	void addMark(MarkAddReq req, Integer code);

	/**
	 * 通过房间id获取标记
	 *
	 * @return {@code List<CrMark>}
	 * @param roomId
	 */
	List<CrMark> getMarkByRoomId(String roomId);

}
