package com.cyberpeace.cr.crbase.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfig;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfigMissionsScheme;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfigRb;
import com.cyberpeace.cr.crbase.service.context.scene.MonitorSession;

public interface CrCommonService {

	/**
	 * 获取监控会话
	 * @return
	 */
	List<MonitorSession> getMonitorSessions(String localSceneId, String studentId);

	/**
	 * 判断中台scene是否已经停止
	 * @param sceneData
	 * @return
	 */
	Boolean isStopped(JSONObject sceneData);

	/**
	 * 校验前端输入的 role config
	 * @param roleConfigs
	 */
	List<String> validateRoleConfig(List<CrRoleConfig> roleConfigs);

	/**
	 * 校验前端输入的 missions scheme
	 * @param missionsScheme
	 * @return 返回校验过的 mission template id 列表
	 */
	List<String> validateMissionsScheme(CrRoleConfigMissionsScheme missionsScheme);

	/**
	 * 校验前端输入的红蓝角色配置
	 * @param roleConfigRb
	 */
	void validateRbRoleConfig(CrRoleConfigRb roleConfigRb);
}
